package com.gls.api.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gls.api.service.DataComService;
import com.gls.api.service.HomeService;
import com.gls.api.service.ProductService;
import com.gls.api.service.UserService;
import com.gls.pojo.ProductParam;
import com.gls.pojo.UserAccount;
import com.gls.pojo.UserBill;
import com.gls.search.util.ResponseResult;
import com.gls.util.CodeUtil;
import com.gls.util.Constants;
import com.gls.util.Loggers;
import com.rabbitmq.client.AMQP.Access.Request;


/**
 * 
 * 文  件  名:HomeController<br/>  
 * 文件描述:首页相关数据<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月24日<br/>
 * 修改内容:<br/>
 */
@Controller  
public class HomeController {	
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HomeService homeService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DataComService dataComService;
	
	
	
	/**
	 * 
	 * 功能描述：TOP10进（出）口行业排名<br/>
	 * 创建人: 詹昌高<br/>
	 * 创建日期:2016年12月24日<br/>
	 * @param param
	 * @return
	 */
    @RequestMapping(value = "/getIndustryTop10",produces = Constants.JSON)
    @ResponseBody
    public String getIndustryTop10(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getIndustryTop10(param);
            //logger.error("TOP10进（出）口行业排名:"+relust);
        }catch(Exception e){
            logger.error("TOP10进（出）口行业排名失败"+e);
        }
        return relust;
    }
    
    /**
     * 
     * 功能描述：TOP10进（出）口行业产品排名<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @param param
     * @return
     */
    @RequestMapping(value = "/getIndustryProductTop10",produces = Constants.JSON)
    @ResponseBody
    public String getIndustryProductTop10(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getIndustryProductTop10(param);
            //logger.error("TOP10进（出）口行业产品排名:"+relust);
        }catch(Exception e){
            logger.error("TOP10进（出）口行业产品排名失败"+e);
        }
        return relust;
        
       
    }
    
    /**
     * 
     * 功能描述：行业出口国占比饼图<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    @RequestMapping(value = "/getIndustryAreaTop10", produces = Constants.JSON)
    @ResponseBody
    public String getIndustryAreaTop10(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getIndustryAreaTop10(param);
            //logger.error("行业出口国占比饼图:"+relust);
        }catch(Exception e){
            logger.error("行业出口国占比饼图失败"+e);
        }
        return relust;
    }
    
    /**
     * 
     * 功能描述：行业产品出口国占饼图<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    @RequestMapping(value = "/getIndustryProductAreaTop10",produces = Constants.JSON)
    @ResponseBody
    public String getIndustryProductAreaTop10(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getIndustryProductAreaTop10(param);
            //logger.error("行业产品出口国占饼图:"+relust);
        }catch(Exception e){
            logger.error("行业产品出口国占饼图失败"+e);
        }
        return relust;
    }
    
    /**
     * 
     * 功能描述：行业下供应商/采购商 所在国家占比饼图 <br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    @RequestMapping(value = "/getIndustrySupplierTop10",produces = Constants.JSON)
    @ResponseBody
    public String getIndustrySupplierTop10(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getIndustrySupplierTop10(param);
            //logger.error("行业下供应商/采购商 所在国家占比饼图:"+relust);
        }catch(Exception e){
            logger.error("行业下供应商/采购商 所在国家占比饼图失败"+e);
        }
        return relust;
    }
    
    /**
     * 
     * 功能描述：获取采购商、供应商总数 ，产品柜量、重量汇总<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    @RequestMapping(value = "/getAggregateAmount",produces = Constants.JSON)
    @ResponseBody
    public String getAggregateAmount(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
    	}*/
        logger.info(param.toString());
        String relust = null;
        try{
            relust = homeService.getAggregateAmount(param);
            //logger.error("获取采购商、供应商总数 ，产品柜量、重量汇总:"+relust);
        }catch(Exception e){
            logger.error("获取采购商、供应商总数 ，产品柜量、重量汇总失败"+e);
        }
        return relust;
    }
	
    
    /**
     * 
     * 功能描述：各地区进口/出口占比图<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    @RequestMapping(value = "/getTurnoverDistributed",produces = Constants.JSON)
    @ResponseBody
    public String getTurnoverDistributed(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getTurnoverDistributed(param);
            //logger.error("各地区进口/出口占比图:"+relust);
        }catch(Exception e){
        	e.printStackTrace();
            logger.error("各地区进口/出口占比图失败"+e);
        }
        return relust;
    }
    
    /**
     * 
     * 功能描述：前十个行业进出口趋势<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    @RequestMapping(value = "/getIndustryTrendTop10",produces = Constants.JSON)
    @ResponseBody
    public String getIndustryTrendTop10(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getIndustryTrendTop10(param);
            //logger.error("前十个行业进出口趋势:"+relust);
        }catch(Exception e){
            logger.error("前十个行业进出口趋势失败"+e);
        }
        return relust;
    }
	
    /**
     * 
     * 功能描述：地区进出口趋势<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    @RequestMapping(value = "/getAreaTurnoverTrend",produces = Constants.JSON)
    @ResponseBody
    public String getAreaTurnoverTrend(@RequestBody ProductParam param){
    	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
        String relust = null;
        try{
            relust = homeService.getAreaTurnoverTrend(param);
            //logger.error("地区进出口趋势:"+relust);
        }catch(Exception e){
            logger.error("地区进出口趋势失败"+e);
        }
        return relust;
    }
	
    /**
     * 
     * 功能描述：获取所有分类<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @return
     */
    @RequestMapping(value = "/getAllCategory",produces = Constants.JSON)
    @ResponseBody
    public String getAllCategory(){
        String relust = null;
        try{
            relust = homeService.getAllCategory();
            //logger.error("获取所有分类:"+relust);
        }catch(Exception e){
            logger.error("获取所有分类失败"+e);
        }
        return relust;
    }
    
    /**
     * 
     * 功能描述：获取地区贸易详情<br/>
     * URL ：/findDistrictTradeDetail <br/>
     * 创建人: Ding<br/>
     * 创建日期:2017年3月9日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/getDistrictTradeDetail" , method=RequestMethod.POST , produces=Constants.JSON)
    @ResponseBody
    public String findDistrictTradeDetail(ProductParam productParam) {
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	Map<String,Object> map = new HashMap<String, Object>();
        String jsonStr=null;
        try {
        	
        		if(userService.checkSee(productParam)){
        			jsonStr = homeService.getDistrictTradeDetail(productParam);
        			logger.info("已经看过了的");
            	}else{
            		boolean flag=userService.deductionIntegral(productParam);
            		if(!flag){
            			
            			map.put(Constants.ERROR_CODE,-1);//扣款失败
            			logger.info("扣款失败");
            	    	jsonStr = ResponseResult.createSuccess(map).toJson();
            		}else{
            		jsonStr = homeService.getDistrictTradeDetail(productParam);
            		}
            	}
        	
        	
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取地区贸易详情"+e);
        }
        return jsonStr;
        
    }
    /**
     * 
     * 功能描述：获取联系 email 和 电话 详情<br/>
     * URL ：/getContactsForEmail <br/>
     * 创建人: meky<br/>
     * 创建日期:2017年4月27日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/getContactsForEmail" , method=RequestMethod.POST , produces=Constants.JSON)
    @ResponseBody
    public Map getContactsForEmail(ProductParam productParam) {
    	Map<String,Object> map = new HashMap<String, Object>();
        
        try {
        	//判断token权限
			if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
				map.put(Constants.ERROR_CODE, Constants.NO_LOGIN_CODE);
				map.put(Constants.ERROR_MSG, Constants.NO_LOGIN_MSG);
				return map;
			}
        	ProductParam pr=new ProductParam();
        	productParam.setId(productParam.getDid());
        	pr=productService.findContactsById(productParam);
        		if(userService.checkSee(productParam)){
        			//查询
        			map.put(Constants.ERROR_CODE,0);//成功
        			map.put("Contacts",pr);
        			logger.info("已经看过了的");
            	}else{
            		
            		if(pr.getEmail().equals("")||pr.getEmail()==null||pr.getLinkPhone().equals("")||pr.getLinkPhone()==null){
            			/*ProductParam prs=new ProductParam();
            			prs.setGuid("n-w75D0CsbLRKMvEhbhNYg");
            			prs.setEmpGuid("sI4Pup_9Rhsc_xsvVj9CLA");
            			prs.setName("Abbate Andrew");
            			prs.setEmpId("75076780");
            			prs.setPosition("Windows Systems Administrator");*/
            			
            			Map<String,String> maps =dataComService.createSearchEmployeeDetail(pr);
            			pr.setLinkPhone(maps.get("phone"));//{phone=+1.408.349.3300, email=andrewa@yahoo-inc.com}
            			pr.setEmail(maps.get("email"));
            			productService.updateContacts(pr);//修改当前的
            		}
            		boolean flag=userService.deductionIntegral(productParam);
                	       if(!flag){
                		     map.put(Constants.ERROR_CODE,-1);//扣款失败
                		     logger.info("扣款失败");
            		        }else{
            		        	map.put(Constants.ERROR_CODE,0);//扣款成功
            		        	map.put("Contacts",pr);
            		        }
            	}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取联系 email 和 电话 详情"+e);
        }
        return map;
        
    }
    /**
	 * 查看是否查看过
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkSee", method = RequestMethod.POST)
	@ResponseBody
	public Map checkSee(ProductParam productParam, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + productParam);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//判断token权限
			if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
				map.put(Constants.ERROR_CODE, Constants.NO_LOGIN_CODE);
				map.put(Constants.ERROR_MSG, Constants.NO_LOGIN_MSG);
				return map;
			}
				if(productParam.getType()==0){
					//'typeDeducInte'   //typeDeductionIntegral操作类型扣除相应积分
					map.put("typeDeducInte",5);
				}else if(productParam.getType()==1){
					//生成报告扣除积分
					map.put("typeDeducInte",100);
				}else{
					map.put("typeDeducInte",100);//暂无
				}
			
			
				if(userService.checkSee(productParam)){
	    			map.put(Constants.ERROR_CODE, 0);
					map.put(Constants.ERROR_MSG,"以查看过");
	        	}else{
	        		map.put(Constants.ERROR_CODE, -1);
					map.put(Constants.ERROR_MSG,"未查看过");
	        	}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				map.put(Constants.ERROR_CODE, Constants.DB_ERROR_CODE);
			}
			if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
				Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
			}
			return map;
	}
	
}
