/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.api.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gls.api.service.redis.RedisService;
import com.gls.api.service.util.CompanyListServer;
import com.gls.api.service.util.EmployeeListServer;
import com.gls.api.service.util.LoginDataCom;
import com.gls.dao.mapper.HomeMapper;
import com.gls.dao.mapper.ProductMapper;
import com.gls.pojo.BusinessProportion;
import com.gls.pojo.CustomPaimingTop10;
import com.gls.pojo.District;
import com.gls.pojo.IndustrySupplierTop10;
import com.gls.pojo.MapClickInfo;
import com.gls.pojo.MapInfo;
import com.gls.pojo.ProductParam;
import com.gls.pojo.ProductSupplierTrend;
import com.gls.pojo.RelationGraph;
import com.gls.pojo.SupplierDistri;
import com.gls.pojo.TransactionDetailed;
import com.gls.pojo.Trend;
import com.gls.search.entity.ProductEntity;
import com.gls.search.util.Page;
import com.gls.search.util.ResponseListResult;
import com.gls.search.util.ResponseResult;
import com.gls.util.CodeUtil;
import com.gls.util.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ProductService extends RedisService{
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired ProductMapper productMapper;
    @Autowired HomeMapper homeMapper;
   
    /**
     *  
     * 功能描述：全球出口/进口服装top10国家排名 柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月24日<br/>
     * @param productParam
     * @return
     */
    public List<CustomPaimingTop10> findProductCountryRanking(ProductParam productParam){
        
        return productMapper.findProductCountryRanking(productParam);
    };
    
    /**
     * 
     * 功能描述：产品进口/出口国占比饼图 柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<IndustrySupplierTop10> findProductProportion(ProductParam productParam){
        
        return productMapper.findProductProportion(productParam);
    };
    
    /**
     * 
     * 功能描述：出口/进口 产品top10 供应商/采购商 排名  柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月24日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findSupplierTop10(ProductParam productParam){
        
        return productMapper.findSupplierTop10(productParam);
    };
    
    /**
     * 
     * 功能描述：产品供应商分布比重图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<SupplierDistri> findSupplierDistri(ProductParam productParam){
        
        return productMapper.findSupplierDistri(productParam);
    };
    
    /**
     * 
     * 功能描述： 供应商/采购商 总数<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public Double findBusinessSum(ProductParam productParam){
        
        return productMapper.findBusinessSum(productParam);
    };
    
    /**
     * 
     * 功能描述：跨境贸易 进口/出口  柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public Double findProductSum(ProductParam productParam){
        
        return productMapper.findProductSum(productParam);
    };
    
    /**
     * 
     * 功能描述：产品Top10供应商 进口/出口 趋势<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Trend> findProductSupplierTrend(ProductParam productParam){
        
        return productMapper.findProductSupplierTrend(productParam);
    };
    
    /**
     * 
     * 功能描述：辅助接口 产品Top10供应商 进口/出口 趋势<br/>
     * URL ： <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月12日<br/>
     * @param productParam
     * @return
     */
    public List<ProductSupplierTrend> findProductSupplierTrend2(ProductParam productParam){
        
        return productMapper.findProductSupplierTrend2(productParam);
    };
    
    
    /**
     * 
     * 功能描述：产品 进口/出口 趋势<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductTrend(ProductParam productParam){
        
        return productMapper.findProductTrend(productParam);
    };
    
    /**
     * 
     * 功能描述：进出口产品Top10 目的国/来源国排名<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年3月2日<br/>
     * @param productParam
     * @return
     */
    public String findProductTop10Destination(ProductParam productParam){
        
        Integer pid = productParam.getPid();
        if (pid==null ) { //进入产品页 pid必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        
        try {
            List<CustomPaimingTop10> listProductTop10Destination = productMapper.findProductTop10Destination(productParam);
            
            jsonStr = ResponseResult.createSuccess(listProductTop10Destination).toJson();
            return jsonStr;
            
        } catch (Exception e) {
            
            jsonStr = ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
            logger.error("Exception : findProductTop10Destination"+e);
            
            return jsonStr;
        }
       
    };
    
    
    /**
     * 
     * 功能描述：产品占比及世界排名<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductProportionAndRanking(ProductParam productParam){
        
        return productMapper.findProductProportionAndRanking(productParam);
    };
    
    /**
     * 
     * 功能描述：获取 进口/出口 总 柜量/重量 (辅助接口，帮助计算排名)<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductCount(ProductParam productParam){
        
        return productMapper.findProductCount(productParam);
    };
    
    /**
     * 
     * 功能描述：进口/出口 产品Top5省份排名 柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductTop5(ProductParam productParam){
        
        return productMapper.findProductTop5(productParam);
    };
    
    /**
     * 
     * 功能描述：进口/出口 产品省份比重 柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductProvince(ProductParam productParam){
        
        return productMapper.findProductProvince(productParam);
    };
    
    /**
     * 
     * 功能描述：Top5省份 进口/出口 产品趋势(获取id)<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<ProductSupplierTrend> findTop5ProvinceTrend(ProductParam productParam){
        
        return productMapper.findTop5ProvinceTrend(productParam);
    };
    /**
     * 
     * 功能描述：辅助接口：Top5省份 进口/出口 产品趋势(获取相应的 柜重量和时间段)<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<Trend> findTop5ProvinceTrend2(ProductParam productParam){
        
        return productMapper.findTop5ProvinceTrend2(productParam);
    };
    
    
    /**
     * 
     * 功能描述：获取地区分类<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月28日<br/>
     * @return
     */
    public List<District> findDistrict1(){
        
        return productMapper.findDistrict1();
    };
    /**
     * 
     * 功能描述：获取地区其他分类<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月28日<br/>
     * @param did
     * @return
     */
    public List<District> findDistrict2(Integer did){
        
        return productMapper.findDistrict2(did);
    };
    
    /**
     * 
     * 功能描述：地图信息<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月28日<br/>
     * @return
     */
    public List<MapInfo> findMapInfo(ProductParam productParam){
        
        return productMapper.findMapInfo(productParam);
    };
    
    /**
     * 
     * 功能描述：地图信息辅助接口获取相关联的国家<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月3日<br/>
     * @param productParam
     * @return
     */
    public List<MapInfo> findMapRelation(ProductParam productParam){
        
        return productMapper.findMapRelation(productParam);
    };
    
    /**
     * 
     * 功能描述：地图信息辅助接口，获取点击地图的具体国家信息<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月10日<br/>
     * @param did
     * @return
     */
    public MapClickInfo findMapClickInfo(Integer did){
        
        return productMapper.findMapClickInfo(did);
    };
    /**
     * 
     * 功能描述：采购商/供应商 信息<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月30日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String, Object>> findBusinessInfo(ProductParam productParam){
        List<Map<String, Object>> result= productMapper.findBusinessInfo(productParam);
        result.get(0).put("pid",productMapper.findBusinessInfoPid(productParam));
        return result;
    };
    
    /**
     * 
     * 功能描述：采购产品比重<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月17日<br/>
     * @param productParam
     * @return
     */
    public List<BusinessProportion> findBusinessProportion1(ProductParam productParam){
        
        return productMapper.findBusinessProportion1(productParam);
    };
    /**
     * 
     * 功能描述：辅助接口：采购产品比重（计算产品总量） <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月17日<br/>
     * @param productParam
     * @return
     */
    public Double findBusinessProportion2(ProductParam productParam){
        
        return productMapper.findBusinessProportion2(productParam);
    };
    
    /**
     * 
     * 功能描述：公司联系人列表<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月17日<br/>
     * @param productParam
     * @return
     */
    public int findContactsCount(ProductParam productParam){
    	return productMapper.findContactsCount(productParam);
    }
    /**
     * 
     * 功能描述：修改 email 和 电话<br/>
     * 创建人: MEKY<br/>
     * 创建日期:2017年4月27日<br/>
     * @param productParam
     * @return
     */
    public int updateContacts(ProductParam productParam){
    	return productMapper.updateContacts(productParam);
    }
    /**
     * 
     * 功能描述：查询 单条联系人记录 通过id <br/>
     * 创建人: MEKY<br/>
     * 创建日期:2017年4月27日<br/>
     * @param productParam
     * @return
     */
    public ProductParam findContactsById(ProductParam productParam){
    	return productMapper.findContactsById(productParam);
    }
    @Transactional
    public List<Map<String, Object>> findContacts(ProductParam productParam){
    	
    	
    	//shangJiaId 公司ID,ietype: 0：采购商，1：供应商
    	//根据公司ID查询公司信息
    	
    	List<Map<String, Object>> comInfo = findBusinessInfo(productParam);
    	
    	if(null != comInfo){
    		
    	}
    	
    	//检查是否从data.com获取过联系人，如果获取过，直接返回联系人列表
//    	if(comInfo.get(0).get("data_flag").toString().equals("0")){
//    		
//    		String businessName = (String) comInfo.get(0).get("name");
//    		
//    		String dnameLevel1 = (String) comInfo.get(0).get("dname_level1");//国家
//    		//String dnameLevel2 = (String) contactsList.get(0).get("dname_level2");
//    		String dnameLevel3 = (String) comInfo.get(0).get("dname_level3");//城市
//    		
//    		String[] comList = Constants.DATA_COM_COM_LIST;//过滤公司国家
//			
//    		//过滤公司的国家范围   	//多条公司结果，根据公司所在国家，城市，匹配是否一致
//    		for (String comCountry : comList) {
//    			if(dnameLevel1.contains(comCountry)){
//    				
//    				String listCompany = seachCompanyList(businessName); //通过公司名模糊匹配DataCom平台 获取公司列表
//    				JsonParser parser = new JsonParser();
//    				JsonArray jsonArr = (JsonArray) parser.parse(listCompany);
//    				
//    				if(jsonArr != null && jsonArr.size() > 0){
//    					
//    					if(jsonArr.size() == 1){
//    						JsonObject jsonObject = jsonArr.get(0).getAsJsonObject();
//    						String activeContacts = jsonObject.get("activeContacts").getAsString(); //公司员工人数
//    						
//    						
//    						if(null != activeContacts){
//    							if(Integer.parseInt(activeContacts) > 0){
//    								//保存公司信息，联系人信息
//    								saveComData(jsonObject,productParam);
//    							}
//    						}
//    						
//    					}else{
//    						int comCount = 0;
//    						JsonObject jsonObjectNew = new JsonObject();
//    						for(int i=0;i<jsonArr.size();i++){
//        						JsonObject jsonObject = jsonArr.get(i).getAsJsonObject();
//        						String activeContacts = jsonObject.get("activeContacts").getAsString(); //公司员工人数
//        						String city = jsonObject.get("city").getAsString().toUpperCase(); //公司所在城市，对应地区表第三级
//        						String country = jsonObject.get("country").getAsString().toUpperCase();//公司所在国家
//        						String name = jsonObject.get("name").getAsString().toUpperCase(); //公司名称
//        						//匹配所属国家，所属城市是否一致，并且联系人数量大于0
//        						if(null != activeContacts && dnameLevel3.contains(city) && Integer.parseInt(activeContacts) > 0){
//        							comCount++;
//        							jsonObjectNew = jsonObject;
//        						}
//        						
//        					}
//    						
//    						if(comCount == 1){
//    							//保存公司信息，联系人信息
//								saveComData(jsonObjectNew,productParam);
//    						}
//    						if(comCount > 1){
//    							for(int i=0;i<jsonArr.size();i++){
//            						JsonObject jsonObject = jsonArr.get(i).getAsJsonObject();
//            						String activeContacts = jsonObject.get("activeContacts").getAsString(); //公司员工人数
//            						String city = jsonObject.get("city").getAsString().toUpperCase(); //公司所在城市，对应地区表第三级
//            						String country = jsonObject.get("country").getAsString().toUpperCase();//公司所在国家
//            						String name = jsonObject.get("name").getAsString().toUpperCase(); //公司名称
//            						//匹配所属国家，所属城市是否一致，并且联系人数量大于0
//            						if(null != activeContacts && dnameLevel3.contains(city) && Integer.parseInt(activeContacts) > 0 && businessName.equals(name.toUpperCase())){
//            							//保存公司信息，联系人信息
//        								saveComData(jsonObject,productParam);
//            						}
//            					}
//    						}
//    					}
//    				}
//    			}
//    		}
//    	}
    	List<Map<String, Object>> contactsList = productMapper.findContacts(productParam);
        return contactsList;
    };
    
    public int saveComData(JsonObject jsonObject,ProductParam productParam){
    	
//    	String city = jsonObject.get("city").getAsString(); //公司所在城市，对应地区表第三级
		String companyWebsite = jsonObject.get("companyWebsite").getAsString();//公司网址
//		String country = jsonObject.get("country").getAsString();//公司所在国家
		String guid = jsonObject.get("guid").getAsString();//公司guid
		String hqPhone = jsonObject.get("hqPhone").getAsString();//公司联系电话
//		String inactive = jsonObject.get("inactive").getAsString(); //未知字段，默认为false
		String name = jsonObject.get("name").getAsString(); //公司名称
//		String state = jsonObject.get("state").getAsString(); // 未知字段
//		String updated = jsonObject.get("updated").getAsString(); //修改日期
		
		Map<String,Object> comMap = new HashMap<String, Object>();
		comMap.put("linkPhone", hqPhone);
		comMap.put("url", companyWebsite);
		comMap.put("dataId", guid);
		comMap.put("dataName", name);
		comMap.put("ietype", productParam.getIetype());
		comMap.put("shangJiaId", productParam.getShangJiaId());
		
		
		//更新公司网址和联系电话,修改日期
		int flag = productMapper.updateBusinessInfo(comMap);
		
		if(flag > 0){
			//根据公司网址获取公司员工列表，保存数据库
			String listEmployees = searchEmployeeList(companyWebsite);
			
			//解析获取到的公司员工Json列表信息，并保存至数据库
			JsonParser parser1 = new JsonParser();
			JsonArray jsonArr2 = (JsonArray) parser1.parse(listEmployees);
			try{
				for(int j=0;j<jsonArr2.size();j++){
					
					JsonObject jsonObjects = jsonArr2.get(j).getAsJsonObject();
					
//					String acquired = jsonObject.get("acquired").getAsString(); //未知字段 默认为null
					String citys = jsonObjects.get("city").getAsString(); //公司所在城市
					String company = jsonObjects.get("company").getAsString(); //公司名称
					String companyGuids = jsonObjects.get("companyGuid").getAsString(); //公司guid
					String companyId = jsonObjects.get("companyId").getAsString(); //公司ID
					String countrys = jsonObjects.get("country").getAsString(); //公司所在国家
//					String directDial = jsonObject.get("directDial").getAsString(); //未知字段  默认为false
//					String guids = jsonObjects.get("guid").getAsString(); //员工guid
					String ids = jsonObjects.get("id").getAsString(); //员工id
//					String inactives = jsonObject.get("inactive").getAsString(); //未知字段  默认为false
					String names = jsonObjects.get("name").getAsString(); // 员工姓名
//					String owned = jsonObject.get("owned").getAsString(); // 未知字段  boolean类型
//					String royaltyAcquired = jsonObject.get("royaltyAcquired").getAsString(); //未知字段  默认为null
//					String states = jsonObject.get("state").getAsString(); //未知字段
					String title = jsonObjects.get("title").getAsString(); //员工职位
					String updateds = jsonObjects.get("updated").getAsString(); //平台修改日期
					
					Map<String,Object> empMap = new HashMap<String, Object>();
					empMap.put("ietype", productParam.getIetype());
					empMap.put("shangJiaId", productParam.getShangJiaId());
					empMap.put("dataComGuid", guid);
					empMap.put("name", names);
					empMap.put("position", title);
					empMap.put("dataEmpId", ids);
					empMap.put("country", countrys);
					empMap.put("city", citys);
					
					
					SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yy");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");
					
					empMap.put("lastTime", sdf2.format(sdf.parse(updateds)));
					
					flag =  productMapper.insertContact(empMap);
				}
			}catch(Exception ex){
				
				logger.error("Exception : 解析员工列表失败;"+ex);
			}
		}else{
			//更新失败
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
    	if(!redisTemplate.hasKey(Constants.REDIS_DATA_LOGIN_COOKIE)){
    		cookieMap = LoginDataCom.autoLogin(Constants.DATA_COM_USER_NAME, Constants.DATA_COM_PASS_WORD);
    		if(cookieMap != null){
    			valueOperations.set(Constants.REDIS_DATA_LOGIN_COOKIE, CodeUtil.transMapToString(cookieMap),60*60,TimeUnit.SECONDS);
    			//默认保留1小时的cookie，重新获取
    		}else {
//    			for (int i = 0; i < 3; i++) {
//    				//尝试继续登陆
//				}
				throw new Exception("data.com登陆失败");
			}
    	}else{
    		cookieMap = CodeUtil.transStringToMap(valueOperations.get(Constants.REDIS_DATA_LOGIN_COOKIE));
    	}
    	return cookieMap;
       	//return LoginDataCom.autoLogin(Constants.DATA_COM_USER_NAME, Constants.DATA_COM_PASS_WORD);
    	
    }
    /*
     * 功能描述：根据公司网址完全匹配查找公司员工列表<br/>
     * 创建人: 田忠华<br/>
     * 创建日期:2017年4月18日<br/>
     * @return
     * */
    public String searchEmployeeList(String companyWebSite){
		
		String employeeList = "";
		
		try{
			
			employeeList = EmployeeListServer.searchPersonList(getDataLoginCookie(),companyWebSite);
			
		}catch(Exception ex){
			
			logger.error("Exception: Search List Employees failed " + ex.getMessage());
			System.out.println("通过公司网址精确搜索公司员工列表失败！ 公司网址为：" + companyWebSite);
		}
		
		return employeeList;
	}
    
    /*
     * 获取DataCom平台通过公司名模糊匹配的公司列表
     * 
     * */
    public String seachCompanyList(String businessName) throws Exception {
    	
    	String listCompany = "";
		
		try{
            businessName = businessName + " ";
            while(true){
                businessName = businessName.substring(0,businessName.lastIndexOf(" "));
                System.out.print(businessName+"-------"+businessName.indexOf(" "));

                listCompany = CompanyListServer.getCompanyList(getDataLoginCookie(),businessName); //模糊匹配 获取公司列表
                if(listCompany!=null && !listCompany.equals("")) {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArr = (JsonArray) parser.parse(listCompany);
                    System.out.print("jsonArr.get(0).getAsJsonObject().get(activeContacts):"+jsonArr.get(0).getAsJsonObject().get("activeContacts"));
                    if(jsonArr.size() > 0  && jsonArr.get(0).getAsJsonObject().get("activeContacts") != null){
                        break;
                    }
                }
                if(businessName.indexOf(" ") == -1){
                    break;
                }

            }

		}catch(Exception ex){
            logger.error("Exception: Serach List Companys fail  " + ex.getMessage());
            System.out.println("模糊搜索公司列表失败！");
            throw ex;
		}
		return listCompany;
    }
    
    /**
     * 
     * 功能描述：详情页 供应商/采购商趋势<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月18日<br/>
     * @return
     */
    public List<ProductSupplierTrend> findBusinessTrendInfo(ProductParam productParam){
        
        return productMapper.findBusinessTrendInfo(productParam);
    };
    public ProductSupplierTrend findBusinessTrendName(ProductParam productParam){
        
        return productMapper.findBusinessTrendName(productParam);
    };
    /**
     * 
     * 功能描述：（辅助接口）详情页 供应商/采购商趋势<br/>
     * URL ： <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月18日<br/>
     * @return
     */
    public List<Trend> findBusinessTrendInfo2(ProductParam productParam){
        
        return productMapper.findBusinessTrendInfo2(productParam);
    };
    
    /**
     * 
     * 功能描述：合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param productParam
     * @return
     */
    public RelationGraph findRelationGraph(ProductParam productParam){
        
        return productMapper.findRelationGraph(productParam);
    };
    /**
     * 
     * 功能描述：（辅助接口：一级）合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param productParam
     * @return
     */
    public List<RelationGraph> findRelationGraph1(ProductParam productParam){
        
        return productMapper.findRelationGraph1(productParam);
    };
    /**
     * 
     * 功能描述：（辅助接口：二级）合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param productParam
     * @return
     */
    public List<RelationGraph> findRelationGraph2(ProductParam productParam){
        
        return productMapper.findRelationGraph2(productParam);
    };
    /**
     * 
     * 功能描述：（辅助接口：三级）合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param productParam
     * @return
     */
    public List<RelationGraph> findRelationGraph3(ProductParam productParam){
        
        return productMapper.findRelationGraph3(productParam);
    };
    
    /**
     * 
     * 功能描述：近一年的交易历史<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String, Object>> findRecentTransaction(ProductParam productParam){
        
        return productMapper.findRecentTransaction(productParam);
    };
    
    /**
     * 
     * 功能描述：与xxx供应商的交易情况<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String, Object>> findTransactionInfo(ProductParam productParam){
        
        return productMapper.findTransactionInfo(productParam);
    };
    /**
     * 
     * 功能描述：近一年的交易历史<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    public Integer findRecentTransactionCount(ProductParam productParam){
        
        return productMapper.findRecentTransactionCount(productParam);
    };
    
    /**
     * 
     * 功能描述：与xxx供应商的交易情况<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    public Integer findTransactionInfoCount(ProductParam productParam){
        
        return productMapper.findTransactionInfoCount(productParam);
    };
    
    /**
     * 
     * 功能描述：竞争对手<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月22日<br/>
     * @param productParam
     * @return
     */
    public List<ProductSupplierTrend> findCompete(ProductParam productParam){
        
        return productMapper.findCompete(productParam);
    };
    
    /**
     * 
     * 功能描述：核心采购商/供应商地区分布图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月23日<br/>
     * @param productParam
     * @return
     */
    public String findbusinessDistribution(ProductParam productParam){
        
      //传递一级二级或者三级的shangJiaIds不可为空
        String shangJiaIds = productParam.getShangJiaIds();
        
        if (shangJiaIds.equals("")) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
       // List<MapInfo> listbusinessDistribution = null;
        try {
            
            //shangJiaIds 代表采购商或供应商id
            String a[] = productParam.getShangJiaIds().split(",");  
          
            List<MapInfo> listproductSupplierTrend=productMapper.findbusinessDistributionByIds(productParam);
            
//            for (int i = 0; i < a.length; i++) {
//                productParam.setShangJiaId(Integer.parseInt(a[i]));
//                List<MapInfo> listbusinessDistribution = productMapper.findbusinessDistribution(productParam);
//                listproductSupplierTrend.addAll(listbusinessDistribution);
//            }
            
            jsonStr = ResponseResult.createSuccess(listproductSupplierTrend).toJson();
            return jsonStr;
        } catch (Exception e) {
            
            logger.error("Exception: findbusinessDistribution" + e.getMessage());
            
            jsonStr = ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
            return jsonStr;
        }
       
    };
    
    /**
     * 
     * 功能描述：交易清单<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年3月8日<br/>
     * @param productParam
     * @return
     */
    public String findTransactionDetailed(ProductParam productParam){
      
        //shangJiaId 代表采购商或供应商id
        Integer shangJiaId = productParam.getShangJiaId();
        productParam.setId(shangJiaId);
        if (shangJiaId==null) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        
        try {
            
            //List<TransactionDetailed> listTransactionDetailed = productMapper.findTransactionDetailed(productParam);
           
            List<TransactionDetailed> listTransactionDetailed = homeMapper.getComDetailListById(productParam);
            //百分比
            DecimalFormat decimalFormat1 = new DecimalFormat("##0.00%");
            DecimalFormat decimalFormat2 = new DecimalFormat("##0.00%");
            
            Double sumVolume = 0.0;
            Double sumTotalweight=0.0;
            
            for (TransactionDetailed transactionDetailed1 : listTransactionDetailed) {
                sumVolume += transactionDetailed1.getTotalVolume();
                
                sumTotalweight += transactionDetailed1.getTotalWeight();
                
            }
            
            for (TransactionDetailed transactionDetailed2 : listTransactionDetailed) {
                
                //转换百分比
                String format = decimalFormat1.format(transactionDetailed2.getTotalVolume()*1.0/sumVolume*1.0);
                transactionDetailed2.setVolumeProportion(format);
                
                String format2 = decimalFormat2.format(transactionDetailed2.getTotalWeight()*1.0/sumTotalweight*1.0);
                transactionDetailed2.setWeightProportion(format2);
                
            }
            
            int total = listTransactionDetailed.size();
            if (total<=0) {
                return "null";
            }
           
            jsonStr = ResponseResult.createSuccess(listTransactionDetailed).toJson();
            
            return jsonStr;
        } catch (Exception e) {
           
            logger.error("Exception: findTransactionDetailed :" + e.getMessage());
            
            jsonStr = ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
            return jsonStr;
        }
       
    }

	public String getSolrGoodsList(Map<String, Object> map) {
		Page<ProductEntity> goodsList = new Page();
		
		List<ProductEntity> list = new ArrayList();
		List list2 = productMapper.findProByStr(map.get("search_keywords")+"%");
		for(int i=0;i<list2.size();i++){
			Map m = (Map) list2.get(i);	
			ProductEntity p = new ProductEntity();
				       p.setProId(Integer.parseInt(m.get("id")+""));
				       p.setPname(m.get("name")+"");
				       System.out.println(p.getPname()+"value= "+ p.getProId());
				       list.add(p);
		}
		goodsList.setObjects(list);
		ResponseListResult createSuccess = ResponseListResult.createSuccess(goodsList.getObjects());
		//createSuccess.setTotal(goodsList.getCounts());
        return createSuccess.toJson();
		
	}
    
}
