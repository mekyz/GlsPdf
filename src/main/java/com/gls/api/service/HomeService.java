/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝格
 * --------------------------------------------------------
 */

package com.gls.api.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gls.api.service.redis.RedisService;
import com.gls.dao.mapper.HomeMapper;
import com.gls.pojo.District;
import com.gls.pojo.IndustryAreaEntity;
import com.gls.pojo.IndustryAreaTop10Entity;
import com.gls.pojo.IndustryProductEntity;
import com.gls.pojo.IndustryStaticTimeTop10;
import com.gls.pojo.IndustrySupplierTop10;
import com.gls.pojo.IndustryTop10Entity;
import com.gls.pojo.ProductCategory;
import com.gls.pojo.ProductParam;
import com.gls.pojo.TenantsAmount;
import com.gls.pojo.TransactionDetailed;
import com.gls.search.util.ResponseResult;
import com.gls.util.Constants;

@Service
public class HomeService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private HomeMapper homeMapper;
    @Autowired
    private RedisService redisService;
    @Resource(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate;
    @Resource(name = "redisTemplate")
    public ValueOperations<String, String> valueOperations;
    @Autowired
    public UserService userService;
    
    
    
    
    /**
     * 
     * 功能描述：TOP10进（出）口行业排名<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    public String getIndustryTop10(ProductParam param){
        String resultStr = "";
        try {
        	/*if (!userService.hasKey(param.getToken(), param.getUserId())) {
        		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
    		}*/
        	
            //TOP10进（出）口行业
        	//REDIS KEY
          // String key  = Constants.REDIS_TRADE_GETINDUSTRYTOP10+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
           // if(redisService.hasKey(key)){
            	//resultStr = valueOperations.get(key);
            //}else{
            	List<IndustryTop10Entity> proList = homeMapper.getIndustryTop10(param);
                resultStr = ResponseResult.createSuccess(proList).toJson();
                //userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
           // }
            
            return resultStr;
          } catch (Exception e) {
              logger.error("Exception: getIndustryProductTop10" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
        
        
    }
    
    
    /**
     * 
     * 功能描述：TOP10进（出）口行业产品排名<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    public String getIndustryProductTop10(ProductParam param){
        if(param.getCid()==null){
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        String resultStr = "";
        try {
        	/* String key = Constants.REDIS_TRADE_GETINDUSTRYTPRODUCTOP10+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
             if(redisService.hasKey(key)){
             	resultStr = valueOperations.get(key);
             }else{*/
            	 List<IndustryProductEntity> proList = homeMapper.getIndustryProductTop10(param);
                 resultStr = ResponseResult.createSuccess(proList).toJson();
               //  userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
            // }
             
            //TOP10进（出）口行业产品
            return resultStr;
          } catch (Exception e) {
              logger.error("Exception: getIndustryProductTop10" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
        
        
    }
    
    /**
     * 
     * 功能描述：行业出口国占比饼图<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    public String getIndustryAreaTop10(ProductParam param){
        if(param.getCid()==null){
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        String resultStr = "";
        try {
        	 //String key =Constants.REDIS_TRADE_GETINDUSTRYAREATOP10+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
             //if(redisService.hasKey(key)){
           	 //resultStr = valueOperations.get(key);
             //}else{
            	 List<IndustryAreaTop10Entity> proList = homeMapper.getIndustryAreaTop10(param);
                 resultStr = ResponseResult.createSuccess(proList).toJson();
               // userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
            // }
            //TOP10进（出）口行业产品
            /*DecimalFormat df1 = new DecimalFormat("##0.00%");
            IndustryTop10Entity valueAmount = homeMapper.getValueAmount(param);
            for (IndustryAreaTop10Entity industryAreaEntity : proList) {
                industryAreaEntity.setValuePercent(df1.format(ShuzhiChangeUtil.dubleToSting(industryAreaEntity.getValue())/ShuzhiChangeUtil.dubleToSting(valueAmount.getValue())));
            }*/
            return resultStr;
          } catch (Exception e) {
              logger.error("Exception: getIndustryAreaTop10" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
    }
    /**
     * 
     * 功能描述：行业产品出口国占饼图<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    public String getIndustryProductAreaTop10(ProductParam param){
        if(param.getCid()==null){
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        String resultStr = "";
        try {
        	// String key =Constants.REDIS_TRADE_GETINDUSTRYPRODUCTAREATOP10+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
            /* if(redisService.hasKey(key)){
             	resultStr = valueOperations.get(key);
             }else{*/
            	 List<IndustryAreaTop10Entity> proList = homeMapper.getIndustryProductAreaTop10(param);
                 resultStr = ResponseResult.createSuccess(proList).toJson();
                 //userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
           //  }
            //TOP10进（出）口行业产品
//            param.setCid(homeMapper.getCategoryLevelId1(param));
//            IndustryTop10Entity valueAmount = homeMapper.getValueAmount(param);
//            DecimalFormat df1 = new DecimalFormat("##0.00%");
//            for (IndustryAreaTop10Entity industryAreaTop10Entity : proList) {
//                industryAreaTop10Entity.setValuePercent(df1.format(ShuzhiChangeUtil.dubleToSting(industryAreaTop10Entity.getValue())/(ShuzhiChangeUtil.dubleToSting(valueAmount.getValue()))));
//            }
            return resultStr;
          } catch (Exception e) {
              logger.error("Exception: getIndustryProductAreaTop10" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
    }
    
    /**
     * 
     * 功能描述：行业下供应商/采购商 所在国家占比饼图 <br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public String getIndustrySupplierTop10(ProductParam param){
        if(param.getCid()==null){
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        String resultStr = "";
        try {
        	/* String key =Constants.REDIS_TRADE_GETINDUSTRYSUPPLIERTOP10+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
             if(redisService.hasKey(key)){
             	resultStr = valueOperations.get(key);
             }else{*/
            	 List<IndustrySupplierTop10> proList = homeMapper.getIndustrySupplierTop10(param);
                 resultStr = ResponseResult.createSuccess(proList).toJson();
               //  userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
            // }
            //行业下供应商/采购商前十排名
           
//            DecimalFormat df1 = new DecimalFormat("##0.00%");
//            IndustryTop10Entity valueAmount = homeMapper.getValueAmount(param);
//            for (IndustrySupplierTop10 industrySupplierTop10 : proList) {
//                industrySupplierTop10.setBaifei(df1.format(industrySupplierTop10.getValue()/ShuzhiChangeUtil.dubleToSting(valueAmount.getValue())));
//            }
            return resultStr;
          } catch (Exception e) {
              logger.error("Exception: getIndustrySupplierTop10" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
    }
    
    /**
     * 
     * 功能描述：获取采购商、供应商总数 ，产品柜量、重量汇总<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public String getAggregateAmount(ProductParam param){
        
        try {
        	String resultStr = "";
        	
        	// String key = Constants.REDIS_TRADE_GETAGGREGATEAMOUNT+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
            // if(redisService.hasKey(key)){
             	//resultStr = valueOperations.get(key);
             //}else{
            	 TenantsAmount tenantsSum = homeMapper.getTenantsAmount(param);//获取商户数量（采购商，供应商）
                 IndustryTop10Entity valueSum = homeMapper.getValueAmount(param);//柜量，重量汇总
                 
                 Map<String, Object> m = new HashMap<String, Object>();
                 m.put("count", tenantsSum.getTenantsAmount());
                 m.put("value", valueSum.getValue());
                 
                 resultStr = ResponseResult.createSuccess(m).toJson();
                // userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
           //  }
             
             return resultStr;
            
            
          } catch (Exception e) {
              logger.error("Exception: getAggregateAmount" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
    }
    
    
    /**
     * 
     * 功能描述：各地区进口/出口占比图<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public String getTurnoverDistributed(ProductParam param){
    	String resultStr = "";
    	 try {
        	// String key =Constants.REDIS_TRADE_GETTURNOVERDISTRIBUTED+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
           //  if(redisService.hasKey(key)){
           //  	resultStr = valueOperations.get(key);
           //  }else{
            	 //各地区进口/出口占比图
                 List<IndustryAreaEntity> proList = homeMapper.getTurnoverDistributed(param);
                 DecimalFormat df1 = new DecimalFormat("##0.00%");
                 IndustryTop10Entity getValueAmount = homeMapper.getValueAmount(param);
                 for (IndustryAreaEntity industryAreaEntity : proList) {
                     industryAreaEntity.setName(industryAreaEntity.getName()+"\n\n"+df1.format(industryAreaEntity.getValue()/getValueAmount.getValue()));
                 }
                 
                 resultStr = JSON.toJSONString(proList);
                // userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
           //  }
             return resultStr;
           
          } catch (Exception e) {
              logger.error("Exception: getIndustrySupplierTop10" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
        
    }
    
    /**
     * 
     * 功能描述：前十个行业进出口趋势<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public String getIndustryTrendTop10(ProductParam param){
        String resultStr = "";
        try {
        	// String key =Constants.REDIS_TRADE_GETINDUSTRYTRENDTOP10+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
        	 
           //  if(redisService.hasKey(key)){
           //  	resultStr = valueOperations.get(key);
           //  }else{
            	 List<IndustryStaticTimeTop10> proList = homeMapper.getIndustryStaticTimeTop10(param);
                 for (IndustryStaticTimeTop10 industryStaticTimeTop10 : proList) {
                     param.setCid(industryStaticTimeTop10.getCid());
                     param.setClevel(industryStaticTimeTop10.getClevel());
                     industryStaticTimeTop10.setStatisList(homeMapper.getIndustryTrendTop10(param));
                 }
                 resultStr = ResponseResult.createSuccess(proList).toJson();
                // userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
             //}
             
            return resultStr;
          } catch (Exception e) {
              logger.error("Exception: getIndustryTrendTop10" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
        
    }
    
    /**
     * 
     * 功能描述：地区进出口趋势<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public String getAreaTurnoverTrend(ProductParam param){
    	 String resultStr = "";
         try {
         	 //String key =Constants.REDIS_TRADE_GETAREATURNOVERTREND+param.getDid()+"-"+param.getCid()+"-"+param.getIetype()+"-"+param.getVwtype();
         	 
              /*if(redisService.hasKey(key)){
             	resultStr = valueOperations.get(key);
              }else{*/
            	  List<IndustryAreaEntity> proList = homeMapper.getAreaTurnoverTrend(param);
                  resultStr = ResponseResult.createSuccess(proList).toJson();
                 // userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
              //}
             
             return resultStr;
          } catch (Exception e) {
              logger.error("Exception: getAreaTurnoverTrend" + e.getMessage());
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
        
    }
    
    /**
     * 
     * 功能描述：获取所有分类<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @return
     */
    public String getAllCategory(){
    	 String resultStr = "";
         try {
         	 String key = Constants.REDIS_TRADE_CATEGORYLIST;
         	 
              if(redisService.hasKey(key)){
              	resultStr = valueOperations.get(key);
              }else{
            	  long startTime = System.currentTimeMillis();
                  //第一级的
                  List<ProductCategory> list = homeMapper.getAllCategoryLevel1();
                  for (ProductCategory category : list) {
                      //第二级别
                      List<ProductCategory> list2 = homeMapper.getAllCategoryLevel2(category.getCid());
                      for (ProductCategory productCategory : list2) {
                        //第三级别
                        List<ProductCategory> list3 = homeMapper.getAllCategoryLevel2(productCategory.getCid());
                        productCategory.setContent(list3);
                      }
                      category.setContent(list2);
                  }
                  long endTime = System.currentTimeMillis();
                  logger.error("cost time>>>>>>>>>>>>>>>>>"+(endTime-startTime));
//                  redisService.setVa("categoryList",JSON.toJSONString(list));
                  resultStr = ResponseResult.createSuccess(list).toJson();
                  userService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
              }
             return resultStr;
//                redisService.setVa("categoryList",JSON.toJSONString(list));
//                return ResponseResult.createSuccess(list).toJson();
//            }
          } catch (Exception e) {
              logger.error("Exception: getAllCategory" + e);
              return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
          }
        
    }
    
    
    /**
     * 
     * @return
     */
    public String getDistrictTradeDetail(ProductParam productParam){
    	String jsonStr=null;
    	
    	if(productParam.getDid() <= 0){
    		 return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
    	}
    	
    	productParam.setPageNo(0);
    	productParam.setPageSize(1000);//最多显示1000条
    	
    	//第一步，查出地区ID相关信息
    	District district = homeMapper.getDistrictById(productParam.getDid());
    	
    	if(district == null){
    		 return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
    	}
    	
    	int fromComCount = 0;//总公司数量
    	int tradeCount = 0;//贸易次数
    	int countryCount = 0;//国家数量
    	int toComCount = 0;//去到的公司数量
    	Double totalVolume = 0.0;//柜量
    	Double totalWeight = 0.0;//重量
    	
    	double volumeProportion = 0;//柜量占比
    	double weightProportion = 0;//重量占比
    	
    	
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("title", district.getDnameEn());//地区名
    	
    	productParam.setDlevel(district.getLevel());
    	
    	//第二步，查询该地区下进出口的公司列表
    	List<TransactionDetailed> comlist = homeMapper.getComListByDid(productParam);
    	
    	fromComCount = comlist.size();
    	
    	TransactionDetailed totalTrans = new TransactionDetailed();
    	totalTrans.setComCount(comlist.size());
    	totalTrans.setTotalVolume(0.0);
    	totalTrans.setTotalWeight(0.0);
    	totalTrans.setCount(0);
    	totalTrans.setVolumeProportion("0");
    	totalTrans.setWeightProportion("0");
    	
    	 DecimalFormat decimalFormat = new DecimalFormat("##0.00");
    	
    	for (TransactionDetailed tmp:comlist) {
    		totalTrans.setTotalVolume(totalTrans.getTotalVolume() + tmp.getTotalVolume());//累计所有公司进出口柜量
        	totalTrans.setTotalWeight(totalTrans.getTotalWeight() + tmp.getTotalWeight());//累计所有公司进出口重量
        	totalTrans.setCount(totalTrans.getCount() + tmp.getCount());
        	tradeCount += tmp.getCount();
        	if(tmp.getProductName().length() > 20){
        		tmp.setProductName(tmp.getProductName().substring(0,20)+"...");
        	}
        	//
		}
    	
    	totalVolume = totalTrans.getTotalVolume();//总过交易柜量
    	totalWeight = totalTrans.getTotalWeight();//总过交易重量
    	
    	totalTrans.setCount(tradeCount);//总过交易重量
    	
    	for (TransactionDetailed tmp:comlist) {
    		tmp.setVolumeProportion(decimalFormat.format(tmp.getTotalVolume()*1.0/totalVolume*100));
        	tmp.setWeightProportion(decimalFormat.format(tmp.getTotalWeight()*1.0/totalWeight*100));
		}
    	
    			
    	Map<String,Object> comListMap = new HashMap<String, Object>();
    	comListMap.put("total", totalTrans);
    	comListMap.put("data", comlist);
    	
    	//////*******************地区数据汇总********************//
    	Map<String,Object> detailMap = new HashMap<String, Object>(); 
    	
    	detailMap.put("fromComCount", fromComCount);//总公司数量
    	
    	
    	
    	
    	
      //////*******************地区数据汇总********************//
    	
    	
    	map.put("comList", comListMap);
    	
    	
    	List<Map<String,Object>> comDetailList = new ArrayList<Map<String,Object>>();
    	
    	Set<String> comSet = new HashSet<String>();//过滤供应商，统计供应商数量。没有其它作用
    	
    	Set<String> dnameSet = new HashSet<String>();//过滤国家名
    	
    	//第三步，查询单个公司的关系公司
    	for (TransactionDetailed tmp:comlist) {
    		productParam.setId(tmp.getId());
        	List<TransactionDetailed> detailList = homeMapper.getComDetailListById(productParam);
        	
        	TransactionDetailed detailTrans = new TransactionDetailed();
        	detailTrans.setComCount(detailList.size());
        	detailTrans.setCount(0);//总过交易重量
        	detailTrans.setTotalVolume(0.0);
        	detailTrans.setTotalWeight(0.0);
        	for (TransactionDetailed tmpDetail : detailList) {
        		comSet.add(tmpDetail.getName());
        		dnameSet.add(tmpDetail.getDname());
        		detailTrans.setTotalVolume(detailTrans.getTotalVolume()+tmpDetail.getTotalVolume());//累计所有公司进出口柜量
        		detailTrans.setTotalWeight(detailTrans.getTotalWeight()+tmpDetail.getTotalWeight());//累计所有公司进出口重量
        		detailTrans.setCount(detailTrans.getCount() + tmp.getCount());
        		if(tmpDetail.getProductName() !=null && tmpDetail.getProductName().length() > 20){
        			tmpDetail.setProductName(tmpDetail.getProductName().substring(0,20)+"...");
        		}
        		
			}
        	
        	for (TransactionDetailed tmpDetail : detailList) {
        		tmpDetail.setVolumeProportion(decimalFormat.format(tmpDetail.getTotalVolume()*1.0/detailTrans.getTotalVolume()*100));
        		tmpDetail.setWeightProportion(decimalFormat.format(tmpDetail.getTotalWeight()*1.0/detailTrans.getTotalWeight()*100));
    		}
        	
        	Map<String,Object> comDetailMap = new HashMap<String, Object>();
        	comDetailMap.put("name", tmp.getName());
        	comDetailMap.put("data", detailList);
        	comDetailMap.put("total", detailTrans);
        	comDetailList.add(comDetailMap);
		}
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	
    	for (String dname : dnameSet) {
    		if(null != dname && !dname.equals("")){
    			Map<String,Object> listMap = new HashMap<String, Object>();
            	listMap.put("dname", dname);
            	listMap.put("cname", "");
            	list.add(listMap);
            	countryCount ++;
    		}
		}
    	detailMap.put("data", list);
    	
    	map.put("comDetailList", comDetailList);
    	
    	detailMap.put("tradeCount", tradeCount);//贸易次数
    	detailMap.put("countryCount", countryCount);//国家数量
    	detailMap.put("toComCount", comSet.size());//去到的公司数量
    	
    	detailMap.put("totalVolume", decimalFormat.format(totalVolume));//柜量
    	detailMap.put("volumeProportion", volumeProportion);//柜量占比
    	detailMap.put("totalWeight", decimalFormat.format(totalWeight));//重量
    	detailMap.put("weightProportion", weightProportion);//占比
    	
    	map.put("statis", detailMap);
    	//汇总数据
    	Map<String,Object> allMap = new HashMap<String, Object>();
    	allMap.put("fromComCount", fromComCount);
    	allMap.put("toComCount", comSet.size());
    	allMap.put("count", tradeCount);
    	allMap.put("totalVolume", decimalFormat.format(totalVolume));
    	allMap.put("volumeProportion", 0);
    	allMap.put("totalWeight", decimalFormat.format(totalWeight));
    	allMap.put("weightProportion", 0);
    	map.put("statisTotal", allMap);
    	map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
    	
    	jsonStr = ResponseResult.createSuccess(map).toJson();
        
        return jsonStr;
    }
    
    
    
}
