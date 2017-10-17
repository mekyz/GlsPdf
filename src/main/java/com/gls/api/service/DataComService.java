package com.gls.api.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Method;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gls.api.service.redis.RedisService;
import com.gls.api.service.util.LoginDataCom;
import com.gls.dao.mapper.ProductMapper;
import com.gls.pojo.ProductParam;
import com.gls.util.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class DataComService  extends RedisService{
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductService productService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<Map<String, Object>> findDataComList(ProductParam productParam) throws Exception {
		List<Map<String, Object>> comList = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> comInfo = productMapper.findBusinessInfo(productParam);
		
		//String[] list = Constants.DATA_COM_COM_LIST;//过滤公司国家
		
		String businessName = (String) comInfo.get(0).get("name");
		//String dnameLevel1 = (String) comInfo.get(0).get("dname_level1");//国家
		
		//判断一下是否在支持的国家范围，不在的话直接返回
		//for (String comCountry : list) {
			//if(dnameLevel1.contains(comCountry)){
				String listCompany = productService.seachCompanyList(businessName); //通过公司名模糊匹配DataCom平台 获取公司列表
				
				if(!listCompany.equals("")){
					JsonParser parser = new JsonParser();
					JsonArray jsonArr = (JsonArray) parser.parse(listCompany);

					int showCount = 100;//默认显示100条
					if(jsonArr.size() <=100){
						showCount = jsonArr.size();
					}

					for(int i=0;i<showCount;i++){
						JsonObject jsonObject = jsonArr.get(i).getAsJsonObject();
						
						Map<String, Object> comMap = new HashMap<String, Object>();
						comMap.put("name", jsonObject.get("name")==null?"":jsonObject.get("name").getAsString());//公司名称
						comMap.put("activeContacts", jsonObject.get("activeContacts")==null?"":jsonObject.get("activeContacts").getAsString());//公司员工人数
						comMap.put("country", jsonObject.get("country")==null?"":jsonObject.get("country").getAsString());//公司所在国家
						comMap.put("city", jsonObject.get("city")==null?"":jsonObject.get("city").getAsString());//公司所在城市，对应地区表第三级
						comMap.put("companyWebsite", jsonObject.get("companyWebsite")==null?"":jsonObject.get("companyWebsite").getAsString());//公司所在城市，对应地区表第三级
						comMap.put("linkPhone", jsonObject.get("hqPhone")==null?"":jsonObject.get("hqPhone").getAsString());////公司联系电话
						comMap.put("guid", jsonObject.get("guid")==null?"":jsonObject.get("guid").getAsString());////公司联系电话
						
						comList.add(comMap);
					}
				}
		//	}
	//	}
		return comList;
	}
	/**
	 * 根据公司公司信息，获取联系人列表，并保存到数据库
	 * 返回联系人列表json
	 * @param productParam
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> getComContact(ProductParam productParam) throws ParseException{
		int count = productMapper.findContactsCount(productParam);
		if(count == 0){
			//根据公司信息查找联系人列表，保存到数据库，返回联系人列表
			saveComData(productParam);
		}
		productParam.setPageNo(0);
		productParam.setPageSize(10);
		List<Map<String, Object>> contactsList = productMapper.findContacts(productParam);
		return contactsList;
	}
	
	 public int saveComData(ProductParam productParam) throws ParseException{
	    	
//	    	String city = jsonObject.get("city").getAsString(); //公司所在城市，对应地区表第三级
			String companyWebsite = productParam.getCompanyWebsite();//公司网址
//			String country = jsonObject.get("country").getAsString();//公司所在国家
			String guid = productParam.getGuid();//公司guid
			String linkPhone = productParam.getLinkPhone();//公司联系电话
//			String inactive = jsonObject.get("inactive").getAsString(); //未知字段，默认为false
			String name = productParam.getName(); //公司名称
//			String state = jsonObject.get("state").getAsString(); // 未知字段
//			String updated = jsonObject.get("updated").getAsString(); //修改日期
			
			Map<String,Object> comMap = new HashMap<String, Object>();
			comMap.put("linkPhone", linkPhone);
			comMap.put("url", companyWebsite);
			comMap.put("dataId", guid);
			comMap.put("dataName", name);
			comMap.put("ietype", productParam.getIetype());
			comMap.put("shangJiaId", productParam.getShangJiaId());
			
			
			//更新公司网址和联系电话,修改日期
			int flag = productMapper.updateBusinessInfo(comMap);
			
			if(flag > 0){
				//根据公司网址获取公司员工列表，保存数据库
				String listEmployees = productService.searchEmployeeList(companyWebsite);
				if(!listEmployees.equals("")){
					//解析获取到的公司员工Json列表信息，并保存至数据库
					JsonParser parser1 = new JsonParser();
					JsonArray jsonArr2 = (JsonArray) parser1.parse(listEmployees);
						for(int j=0;j<jsonArr2.size();j++){
							
							JsonObject jsonObjects = jsonArr2.get(j).getAsJsonObject();
							
	//						String acquired = jsonObject.get("acquired").getAsString(); //未知字段 默认为null
							String citys = jsonObjects.get("city").getAsString(); //公司所在城市
							String company = jsonObjects.get("company").getAsString(); //公司名称
							String companyGuids = jsonObjects.get("companyGuid").getAsString(); //公司guid
							String companyId = jsonObjects.get("companyId").getAsString(); //公司ID
							String countrys = jsonObjects.get("country").getAsString(); //公司所在国家
	//						String directDial = jsonObject.get("directDial").getAsString(); //未知字段  默认为false
							String guids = jsonObjects.get("guid").getAsString(); //员工guid
							String ids = jsonObjects.get("id").getAsString(); //员工id
	//						String inactives = jsonObject.get("inactive").getAsString(); //未知字段  默认为false
							String names = jsonObjects.get("name").getAsString(); // 员工姓名
	//						String owned = jsonObject.get("owned").getAsString(); // 未知字段  boolean类型
	//						String royaltyAcquired = jsonObject.get("royaltyAcquired").getAsString(); //未知字段  默认为null
	//						String states = jsonObject.get("state").getAsString(); //未知字段
							String title = jsonObjects.get("title").getAsString(); //员工职位
							String updateds = jsonObjects.get("updated").getAsString(); //平台修改日期
							
							Map<String,Object> empMap = new HashMap<String, Object>();
							empMap.put("ietype", productParam.getIetype());
							empMap.put("shangJiaId", productParam.getShangJiaId());
							empMap.put("dataComGuid", guid);
							empMap.put("name", names);
							empMap.put("position", title);
							empMap.put("dataEmpId", ids);
							empMap.put("dataEmpGuid", guids);
							empMap.put("country", countrys);
							empMap.put("city", citys);
							
							
							SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yy");
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");
							
							empMap.put("lastTime", sdf2.format(sdf.parse(updateds)));
							
							flag =  productMapper.insertContact(empMap);
							if(flag<=0){
								logger.error("更新公司联系人信息失败");
							}
						}
					}
			}else{
				//更新失败
				logger.error("更新公司信息失败");
			}
			
	    	return flag;
	    }
	
	/**
     * data.com登陆cookie
     * @return
     * @throws Exception
     */
    public Map<String,String> getDataLoginCookie() throws Exception{
    	Map<String,String> cookieMap = null;
    	System.out.println(cookieMap+">>>...");
    	//if(!redisTemplate.hasKey(Constants.REDIS_DATA_LOGIN_COOKIE)){
    		cookieMap = LoginDataCom.autoLogin(Constants.DATA_COM_USER_NAME, Constants.DATA_COM_PASS_WORD);
    		System.out.println(cookieMap+">>>...");
    		if(cookieMap != null){
    			//hashOperations.putAll(Constants.REDIS_DATA_LOGIN_COOKIE, cookieMap);
    		}else {
//    			for (int i = 0; i < 3; i++) {
//    				//尝试继续登陆
//				}
				throw new Exception("data.com登陆失败");
			}
    	//}else{
    	//	cookieMap = hashOperations.entries("DataUserCookie");
    	//}
    	
    	return cookieMap;
    }
    
   
	public Map<String,String> createSearchEmployeeDetail(ProductParam productParam){
		
		Map<String,String> paramMap = new  HashMap<String,String>();
		
		/*paramMap.put("company_guid", "n-w75D0CsbLRKMvEhbhNYg");
		paramMap.put("person_guid", "sI4Pup_9Rhsc_xsvVj9CLA");
		paramMap.put("firstName", "Abbate");
		paramMap.put("lastName", "Andrew");
		paramMap.put("personId", "75076780");
		paramMap.put("title", "Windows Systems Administrator");*/
		String lastName="";
		String fastName="";
		String[] name=productParam.getName().split(" ");
		lastName=name[0];
		fastName=name[1];
		
		paramMap.put("company_guid", productParam.getGuid());
		paramMap.put("person_guid", productParam.getEmpGuid());
		paramMap.put("firstName",fastName);
		paramMap.put("lastName", lastName);
		paramMap.put("personId", productParam.getEmpId());
		paramMap.put("title", productParam.getPosition());
		//System.out.println(searchEmployeeDetail(paramMap));
		return  searchEmployeeDetail(paramMap);
	}

public Map<String,String> searchEmployeeDetail(Map<String,String> param){
		
		String employeeDetail = "";
		
		Map<String,String> detailData = new HashMap<String,String>();
		try{
			
			//COOKIE_MAP = LoginDataCom.autoLogin(USER_NAME, PASS_WORD);  //自动登录，获取CookieMap
			Map<String,String> COOKIE_MAP = getDataLoginCookie();
			
			String CSRF_TOKEN = COOKIE_MAP.get("CSRF_TOKEN");//下一步处理COOKIE_MAP时会移除CSRF_TOKEN，先备份，下一步再重新放入COOKIE_MAP
			
			employeeDetail = getPersonDetail(COOKIE_MAP,param);
			
			COOKIE_MAP.put("CSRF_TOKEN", CSRF_TOKEN); //重新把CSRF_TOKEN放入COOKIE_MAP
			
			if(employeeDetail.contains("You don't have enough points to get this contact.")){
				
				System.out.println("判断当前账号充足：employeeDetail " +employeeDetail);
				
				System.out.println("当前账户余额不足,获取不了当前用户信息");
				
			}else if(employeeDetail.contains("You already own this contact.")){
				
				System.out.println("判断是否已经获取：employeeDetail = " + employeeDetail);
				
				System.out.println("已获取当前用户，请移至数据库查询");
				
			}
				
				//String detailHtml = EmployeeIContactServer.getPersonDetailss(COOKIE_MAP,param,CSRF_TOKEN);
				detailData = getDetailData(employeeDetail);
				
		}catch(Exception ex){

			ex.printStackTrace();
			logger.error("Exception: Search Employee Detail Infomation Failed " + ex.getMessage());
			System.out.println("通过员工guid获取员工的手机号和邮箱信息失败");
		}
		
		return detailData;
	}
	public Map<String,String> getDetailData(String employeeDetail){
		
		Document doc = Jsoup.parse(employeeDetail);
		Map<String,String> info = new HashMap<String,String>();
		
		Element divContainer = doc.getElementsByClass("businesscard-background").get(0);
		String number = divContainer.getElementById("contactInfoPhone").text();
		String email = divContainer.getElementsByClass("businesscard-contactinfo-email").get(0).getElementsByTag("a").get(0).text();
		info.put("phone", number);
		info.put("email", email);
		
		return info;
	}

public static String getPersonDetailss(Map<String,String> cookieMapNew,Map<String,String> listParam,String CSRF_TOKEN){
		
		String personDetailUrl = "https://connect.data.com/contact/view/"+listParam.get("person_guid")+"?fromAddUpdateFlow=true&action=purchase";
		//String personDetailUrl = "https://connect.data.com/contact/purchase";
		
		String htmlDoc = "";
		
		try{
			
			Map<String,String> dataMap = dataMapss(cookieMapNew,listParam,CSRF_TOKEN);
			
			Map<String,String> headMap = headMap(cookieMapNew,listParam.get("company_guid"));
			
			htmlDoc = Jsoup.connect(personDetailUrl)
					
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36")
					
					.headers(headMap)
					
					.data(dataMap)
					
					.ignoreContentType(true)
					
					.method(Method.POST)
					
					.execute().body();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return htmlDoc;
	}

	public static String getPersonDetail(Map<String,String> cookieMapNew,Map<String,String> listParam){
		
		String personDetailUrl = "https://connect.data.com/contact/purchase";
		
		String htmlDoc = "";
		
		try{
			
			Map<String,String> dataMap = dataMap(cookieMapNew,listParam);
			
			cookieMapNew.remove("CSRF_TOKEN");
			
			Map<String,String> headMap = headMap(cookieMapNew,listParam.get("company_guid"));
			
			htmlDoc = Jsoup.connect(personDetailUrl)
					
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36")
					
					.headers(headMap)
					
					.data(dataMap)
					
					.ignoreContentType(true)
					
					.method(Method.POST)
					
					.execute().body();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return htmlDoc;
	}
	
public static Map<String,String> dataMapss(Map<String,String> cookieMapNew,Map<String,String> param,String CSRF_TOKEN) throws IOException{
		
		Map<String,String> dataMap = new HashMap<String,String>();
		
		dataMap.put("businessCardVersionModel.contactVersionModel.firstName",param.get("firstName"));
		dataMap.put("businessCardVersionModel.contactVersionModel.lastName",param.get("lastName"));
		dataMap.put("countryId","9000");
		dataMap.put("contactIdOrGuid",param.get("personId")); 
		dataMap.put("contactId",param.get("personId"));
		dataMap.put("businessCardVersionModel.contactVersionModel.title",param.get("title"));
		dataMap.put("businessCardVersionModel.contactVersionModel.version","0");
		dataMap.put("CSRF_TOKEN",cookieMapNew.get("CSRF_TOKEN"));
		
		return dataMap;
	}

	public static Map<String,String> dataMap(Map<String,String> cookieMapNew,Map<String,String> param) throws IOException{
		
		Map<String,String> dataMap = new HashMap<String,String>();
		
		dataMap.put("businessCardVersionModel.contactVersionModel.firstName",param.get("firstName"));
		dataMap.put("businessCardVersionModel.contactVersionModel.lastName",param.get("lastName"));
		dataMap.put("countryId","9000");
		dataMap.put("contactIdOrGuid",param.get("personId")); 
		dataMap.put("contactId",param.get("personId"));
		dataMap.put("businessCardVersionModel.contactVersionModel.title",param.get("title"));
		dataMap.put("businessCardVersionModel.contactVersionModel.version","0");
		dataMap.put("CSRF_TOKEN",cookieMapNew.get("CSRF_TOKEN"));
		
		return dataMap;
	}
	
	public static Map<String,String> headMap(Map<String,String> cookieMap,String param){
		
		Map<String,String> headMap = new HashMap<String,String>();
		
		String cookieStr = cookieMap.toString().replace(",",";").replace("{", "").replace("}", "");
		headMap.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headMap.put("Accept-Encoding","gzip, deflate, sdch, br");
		headMap.put("Accept-Language","en,zh-CN;q=0.8,zh;q=0.6");
		headMap.put("Cookie",cookieStr);
		headMap.put("Cache-Control","max-age=0");
		headMap.put("Connection","keep-alive");
		headMap.put("Content-Type","application/x-www-form-urlencoded");
		headMap.put("Host","connect.data.com");
		headMap.put("Origin","https://connect.data.com");
		headMap.put("Referer","https://connect.data.com/contact/"+param+"?channel=Contact-Business%20card:r2v1");
		headMap.put("Upgrade-Insecure-Requests","1");
		
		return headMap;
	}
}
