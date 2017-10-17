/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.api.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gls.api.service.ProductService;
import com.gls.api.service.SearchNewService;
import com.gls.api.service.UserService;
import com.gls.api.service.redis.RedisService;
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
import com.gls.pojo.SupplierDistri2;
import com.gls.pojo.Trend;
import com.gls.search.util.ResponseResult;
import com.gls.util.Constants;


@Controller
public class ProductController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired ProductService productService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    /**
     * 
     * 功能描述：全球出口/进口产品top10国家排名 柜量/重量<br/>
     * URL ： /findProductCountryRanking.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月24日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductCountryRanking.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductCountryRanking(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        String jsonStr=null;
        try{
            
            List<CustomPaimingTop10> listProductCountryRanking = productService.findProductCountryRanking(productParam);
            
            if (listProductCountryRanking.size()<1) {
                logger.error("数据为空");
            }
            jsonStr = ResponseResult.createSuccess(listProductCountryRanking).toJson();
            
        }catch(Exception e){
            logger.error("全球出口/进口产品top10国家排名 :"+e);
        }
       
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：产品进口/出口国占比饼图 柜量/重量 <br/>
     * URL ： /findProductProportion.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月24日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductProportion.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductProportion(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	long startTime = System.currentTimeMillis();
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        String baifenbi = "";// 接受百分比的值 
        try{
            List<IndustrySupplierTop10> listProductProportion = productService.findProductProportion(productParam);
            
            for (IndustrySupplierTop10 industrySupplierTop10 : listProductProportion) {
                DecimalFormat df1 = new DecimalFormat("##0.00%");
                baifenbi = df1.format(industrySupplierTop10.getValue()/productService.findProductSum(productParam)); 
                industrySupplierTop10.setBaifei(baifenbi);
            }
            
            jsonStr = ResponseResult.createSuccess(listProductProportion).toJson();
            
        }catch(Exception e){
            logger.error("产品进口/出口国占比饼图 柜量/重量 :"+e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("产品进口/出口国占比饼图 柜量/重量 :cost time"+(endTime-startTime));
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：出口/进口 产品top10 供应商/采购商 排名  柜量/重量<br/>
     * URL ：/findSupplierTop10.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findSupplierTop10.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findSupplierTop10(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	long startTime = System.currentTimeMillis();
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            
            List<Map<String , Object>> listSupplierTop10 = productService.findSupplierTop10(productParam);
        
            map.put(Constants.PING_DATA, listSupplierTop10);
            map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
           
            JSONObject json =new JSONObject(map);
            jsonStr = json.toString();
            
        }catch(Exception e){
            logger.error("出口/进口 产品top10 供应商/采购商 排名  柜量/重量 :"+e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("出口/进口 产品top10 供应商/采购商 排名  柜量/重量 :cost time"+(endTime-startTime));
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：产品供应商分布比重<br/>
     * URL ： findSupplierDistri.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findSupplierDistri.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findSupplierDistri(@RequestBody ProductParam productParam,HttpServletRequest request){
    	/*if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}*/
    	long startTime = System.currentTimeMillis();
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
            
            List<SupplierDistri> listSupplierDistri =  productService.findSupplierDistri(productParam);
            List<SupplierDistri2> listSup2 = new ArrayList<>();
            Double totalCompany = productService.findBusinessSum(productParam);
            for(int i=0;i<listSupplierDistri.size();i++){
            	SupplierDistri supplierDistri = listSupplierDistri.get(i);
            	SupplierDistri2 supplierDistri2 = new SupplierDistri2();
            	DecimalFormat df1 = new DecimalFormat("##0.00%");
                supplierDistri2.setName(supplierDistri.getName()+"\n\n"+df1.format(supplierDistri.getValue()/totalCompany));
                supplierDistri2.setValue(supplierDistri.getValue());
                listSup2.add(supplierDistri2);
            }
            jsonStr = ResponseResult.createSuccess(listSup2).toJson();
        }catch(Exception e){
            logger.error(" 产品供应商分布比重 :"+e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("产品供应商分布比重 :cost time"+(endTime-startTime));
       
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：供应商/采购商 总数<br/>
     * URL ： /findBusinessSum.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findBusinessSum.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findBusinessSum(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	long startTime = System.currentTimeMillis();
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
           Map<String, Object> map = new HashMap<String, Object>();
            
           Double listBusinessSum = productService.findBusinessSum(productParam);
           Double listProductSum = productService.findProductSum(productParam);
           
           map.put("count", listBusinessSum);
           map.put("value", listProductSum);
           
           jsonStr = ResponseResult.createSuccess(map).toJson();
            
        }catch(Exception e){
            logger.error(" 供应商/采购商 总数 :"+e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("供应商/采购商 总数 :cost time"+(endTime-startTime));
        return jsonStr; 
    }
    
    /**
     * 与供应商/采购商 总数融合一个接口
     * 功能描述：跨境贸易 进口/出口  柜量/重量<br/>
     * URL ： /findProductSum.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
//    @RequestMapping(value = "/findProductSum.php",method = RequestMethod.POST,produces = Constants.JSON)
//    @ResponseBody
//    public String findProductSum(@RequestBody ProductParam productParam,HttpServletRequest request){
//        
//        Integer cid = productParam.getCid();
//        if (cid==null) { //必传字段
//            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
//        }
//        
//        String jsonStr=null;
//        try{
//            Map<String, Object> map = new HashMap<String, Object>();
//            
//            Double listProductSum = productService.findProductSum(productParam);
//        
//            map.put(Constants.PING_DATA, listProductSum);
//            map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
//           
//            JSONObject json =new JSONObject(map);
//            jsonStr = json.toString();
//            logger.info(" 跨境贸易 进口/出口  柜量/重量 :"+jsonStr);
//            
//        }catch(Exception e){
//            logger.error(" 跨境贸易 进口/出口  柜量/重量 :"+e);
//        }
//       
//        return jsonStr; 
//    }
    
    /**
     * 
     * 功能描述：产品Top10供应商 进口/出口 趋势<br/>
     * URL ： /findProductSupplierTrend.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月24日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductSupplierTrend.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductSupplierTrend(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	long startTime = System.currentTimeMillis();
        Integer pid = productParam.getPid();
        if (pid==null ) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
            //获取id
            List<ProductSupplierTrend> findProductSupplierTrend2 = productService.findProductSupplierTrend2(productParam);
            
            List<Trend> listProductSupplierTrend=null;
            
            for (ProductSupplierTrend integer : findProductSupplierTrend2) {
                //<!--传供应商或者采购商的id   -->
                productParam.setShangJiaId(integer.getId());
                
                listProductSupplierTrend = productService.findProductSupplierTrend(productParam);

                integer.setTrends(listProductSupplierTrend);
            }
           
            jsonStr = ResponseResult.createSuccess(findProductSupplierTrend2).toJson();
            
        }catch(Exception e){
            logger.error("产品Top10供应商 进口/出口 趋势 :"+e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("产品Top10供应商 进口/出口 趋势 :cost time"+(endTime-startTime));
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：产品 进口/出口 趋势<br/>
     * URL ： /findProductTrend.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月24日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductTrend.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductTrend(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	long startTime = System.currentTimeMillis();
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            
            List<Map<String , Object>> listProductTrend = productService.findProductTrend(productParam);
        
            map.put(Constants.PING_DATA, listProductTrend);
            map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
           
            JSONObject json =new JSONObject(map);
            jsonStr = json.toString();
            
        }catch(Exception e){
            logger.error("产品 进口/出口 趋势 :"+e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("产品 进口/出口 趋势:cost time"+(endTime-startTime));
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：进出口产品Top10 目的国/来源国排名<br/>
     * URL ： /findProductTop10Destination.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年3月2日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductTop10Destination.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductTop10Destination(@RequestBody ProductParam productParam){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	long startTime = System.currentTimeMillis();
        String jsonStr=null;
        try {
            
            jsonStr = productService.findProductTop10Destination(productParam);
            
        } catch (Exception e) {

            logger.error("进出口产品Top10 目的国/来源国排名  失败:"+e);
            
        }
        long endTime = System.currentTimeMillis();
        logger.info("进出口产品Top10 目的国/来源国排名:cost time"+(endTime-startTime));
        return jsonStr;
        
    }
    
    
    /**
     * 
     * 功能描述：产品占比及世界排名 <br/>
     * URL ： /findProductProportionAndRanking.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductProportionAndRanking.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductProportionAndRanking(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            
            List<Map<String , Object>> listProductProportionAndRanking = productService.findProductProportionAndRanking(productParam);
            //获取 进口/出口 总 柜量/重量 (辅助接口，帮助计算排名)
            List<Map<String,Object>> listProductCount = productService.findProductCount(productParam);
            
            map.put("value", listProductProportionAndRanking);
            map.put("count", listProductCount);
            map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
           
            JSONObject json =new JSONObject(map);
            jsonStr = json.toString();
            
        }catch(Exception e){
            logger.error("产品占比及世界排名 :"+e);
        }
       
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：进口/出口 产品Top5省份排名 柜量/重量<br/>
     * URL ： /findProductTop5.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductTop5.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductTop5(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            
            List<Map<String , Object>> listProductTop5 = productService.findProductTop5(productParam);
          
                map.put(Constants.PING_DATA, listProductTop5);
                map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
               
                JSONObject json =new JSONObject(map);
                jsonStr = json.toString();
              
                                
        }catch(Exception e){
            logger.error("进口/出口 产品Top5省份排名 柜量/重量 :"+e);
        }
       
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：进口/出口 产品省份比重 柜量/重量<br/>
     * URL ： /findProductProvince.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findProductProvince.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findProductProvince(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            
            List<Map<String , Object>> listProductProvince = productService.findProductProvince(productParam);
        
            map.put(Constants.PING_DATA, listProductProvince);
            map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
           
            JSONObject json =new JSONObject(map);
            jsonStr = json.toString();
            
        }catch(Exception e){
            logger.error("进口/出口 产品省份比重 柜量/重量 :"+e);
        }
       
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：Top5省份 进口/出口 产品趋势<br/>
     * URL ： /findTop5ProvinceTrend.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findTop5ProvinceTrend.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findTop5ProvinceTrend(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
        Integer pid = productParam.getPid();
        if (pid==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
           
            //获取did
            List<ProductSupplierTrend> listTop5ProvinceTrend = productService.findTop5ProvinceTrend(productParam);
            for (ProductSupplierTrend productSupplierTrend : listTop5ProvinceTrend) {
                productParam.setDid(productSupplierTrend.getId());
                //获取相应时间段柜重量
               List<Trend> findTop5ProvinceTrend2 = productService.findTop5ProvinceTrend2(productParam);
               productSupplierTrend.setTrends(findTop5ProvinceTrend2);
            }
        
            jsonStr = ResponseResult.createSuccess(listTop5ProvinceTrend).toJson();
            
        }catch(Exception e){
            logger.error("Top5省份 进口/出口 产品趋势 :"+e);
        }
       
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：获取地区分类<br/>
     * URL ：/findDistrict1.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月28日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findDistrict1.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findDistrict1(){
        String jsonStr=null;
        try{
            if(redisService.getValue("areaList")!=null){
                jsonStr = redisService.getValue("areaList");
            }else{
            //  一级
                List<District> list1 = productService.findDistrict1();
                for (District district1 : list1) {
                    //第二级别
                    List<District> list2 = productService.findDistrict2(district1.getDid());
                    for (District district2 : list2) {
                      //第三级别
                      List<District> list3 = productService.findDistrict2(district2.getDid());
                          for (District district3 : list3) {
                              List<District> list4 = productService.findDistrict2(district3.getDid());
                              district3.setContent(list4);
                          }
                      district2.setContent(list3);
                      
                    };
                    district1.setContent(list2);
                };

               jsonStr=JSON.toJSONString(list1);
               redisService.setVa("areaList", jsonStr);
            }
             
        }catch(Exception e){
            logger.error("地区分类 :"+e);
        };
       
        return jsonStr ; 
    }
    
    /**
     * 
     * 功能描述：地图信息<br/>
     * URL ： /findMapInfo.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return          
     */
    @RequestMapping(value = "/findMapInfo.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findMapInfo(@RequestBody ProductParam productParam,HttpServletRequest request){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
    	long startTime = System.currentTimeMillis();
        String resultStr=null;
        
        try{
        	String key = Constants.REDIS_TRADE_MAPINFO + productParam.getDid()+"-"+productParam.getCid()+"-"+productParam.getIetype();
        	 if(redisService.hasKey(key)){
        		 resultStr = redisService.getValue(key);
             }else{
	            List<MapInfo> listMapInfo = productService.findMapInfo(productParam);
	        
	            resultStr = ResponseResult.createSuccess(listMapInfo).toJson();
	            redisService.setValueAndExpire(key, resultStr, Constants.REDIS_DATA_CACHE_TIME);
             }
            return resultStr;
        }catch(Exception e){
            logger.error("地图信息 :"+e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("地图信息:cost time"+(endTime-startTime));
        return resultStr; 
    }
    
    /**
     * 
     * 功能描述：地图信息辅助接口获取相关联的国家<br/>
     * URL ：/findMapRelation.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月3日<br/>
     * @param did
     * @param request
     * @return
     */
    @RequestMapping(value = "/findMapRelation.php" ,method = RequestMethod.POST ,produces = Constants.JSON)
    @ResponseBody
    public String findMapRelation(@RequestBody ProductParam productParam , HttpServletRequest request){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
        Integer did=productParam.getDid();
        if (did==null) { //必传字段
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try{
           // Map<String, Object> map = new HashMap<String, Object>();
        	String key = Constants.REDIS_TRADE_FINDMAPRELATION + productParam.getDid();
        	if(redisService.hasKey(key)){
        		jsonStr = redisService.getValue(key);
            }else{
	            List<MapInfo> listMapRelation = productService.findMapRelation(productParam);
	            MapClickInfo listMapClickInfo = productService.findMapClickInfo(did);
	            
	            int value = 0;
	            for (MapInfo mapInfo: listMapRelation) {
	            	value += mapInfo.getValue();
					
				}
	            listMapClickInfo.setValue(value);
	            listMapClickInfo.setMapInfo(listMapRelation);
	        
	            jsonStr = ResponseResult.createSuccess(listMapClickInfo).toJson();
	            redisService.setValueAndExpire(key, jsonStr, Constants.REDIS_DATA_CACHE_TIME);
            }
            
        }catch(Exception e){
            logger.error("地图信息获取相关联的国家 :"+e);
        }
       
        return jsonStr; 
    }
    
    
    /**
     * 
     * 功能描述：采购商/供应商 信息<br/>
     * URL ： /findBusinessInfo.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @param request
     * @return
     */
    @RequestMapping(value = "/findBusinessInfo.php",method = RequestMethod.POST,produces = Constants.JSON)
    @ResponseBody
    public String findBusinessInfo(@RequestBody ProductParam productParam,HttpServletRequest request){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
        //shangJiaId 代表采购商或供应商id
    	  Integer shangJiaId = productParam.getShangJiaId();
          if (shangJiaId==null) {//必传字段，不可为空
              return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
          }
          String jsonStr=null;
          
    	if(productParam.getProKey()!=null&&(!"".equals(productParam.getProKey()))){
    		SearchNewService.queryProportionByProKey(productParam,2);
    	}else{
    		 try{
    	            List<Map<String, Object>> listBusinessInfo = productService.findBusinessInfo(productParam);
    	        
    	            jsonStr = ResponseResult.createSuccess(listBusinessInfo).toJson();
    	            
    	        }catch(Exception e){
    	            logger.error("采购商/供应商 信息 :"+e);
    	        }
    	}
      
        
       
       
        return jsonStr; 
    }
    
    /**
     * 
     * 功能描述：采购产品比重<br/>
     * URL ：/findBusinessProportion1.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月17日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findBusinessProportion1.php",method=RequestMethod.POST,produces=Constants.JSON)
    @ResponseBody
    public String findBusinessProportion1(@RequestBody ProductParam productParam){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
        //shangJiaId 代表采购商或供应商id
        Integer shangJiaId = productParam.getShangJiaId();
        if (shangJiaId==null) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        List<BusinessProportion> listBusinessProportion1 = new ArrayList();
        if(productParam.getProKey()!=null&&(!"".equals(productParam.getProKey()))){
        	listBusinessProportion1 =SearchNewService.queryProportionByProKey(productParam,2);
    	}else{
    		 //采购产品比重
    		listBusinessProportion1 = productService.findBusinessProportion1(productParam);
            //辅助接口：采购产品比重（计算产品总量）
            Double listBusinessProportion2 = productService.findBusinessProportion2(productParam);
            if (listBusinessProportion2==null) {
                logger.error("辅助接口：计算产品总量 "+listBusinessProportion2);
                return "0";
            }
            DecimalFormat decimalFormat=new DecimalFormat("##0.00%");
           for (BusinessProportion businessProportion : listBusinessProportion1) {
               if (businessProportion.getValue()!=0) {
                   String baifen = decimalFormat.format(businessProportion.getValue()/listBusinessProportion2);
                   businessProportion.setBaifenbi(baifen);
                   
            }else {
                
                businessProportion.setBaifenbi("0.0%");
            }
               
        }
    	}
        try {
           
           jsonStr = ResponseResult.createSuccess(listBusinessProportion1).toJson(); 
        } catch (Exception e) {
            logger.error("采购产品比重 :"+jsonStr);
        }
        return jsonStr;
    }
    /**
     * 
     * 功能描述：公司联系人列表<br/>
     * URL ：/findContacts.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findContacts.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findContacts(@RequestBody ProductParam productParam){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
      //shangJiaId 代表采购商或供应商id
        Integer shangJiaId = productParam.getShangJiaId();
        if (shangJiaId==null) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        if(productParam.getPageNo()!=null && productParam.getPageSize() !=null){
            productParam.setPageNo((productParam.getPageNo()-1)*productParam.getPageSize());
            
        }else{ //默认
           productParam.setPageNo(0);
           productParam.setPageSize(10);
        }
        
        String jsonStr=null;
        try {
            //获取联系人列表
            List<Map<String, Object>> listContacts = productService.findContacts(productParam);
          //获取联系人列表
            int count = productService.findContactsCount(productParam);
            jsonStr = ResponseResult.createSuccess(listContacts,count).toJson();
        } catch (Exception e) {
            
            logger.error("公司联系人列表 :"+jsonStr);
        }
        return jsonStr;
    }
    
    /**
     * 
     * 功能描述：详情页 供应商/采购商趋势<br/>
     * URL ： /findBusinessTrendInfo.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findBusinessTrendInfo.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findBusinessTrendInfo(@RequestBody ProductParam productParam){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
       //传递一级二级或者三级的shangjiaIds不可为空
        String shangJiaIds = productParam.getShangJiaIds();
        
        if (shangJiaIds.equals("")) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        
        String jsonStr=null;
        try {
        //shangJiaIds 代表采购商或供应商id
        String tmp[] = productParam.getShangJiaIds().split(",");  
        
        //去重
        Set<String> set = new HashSet<String>();  
        for(int i=0;i<tmp.length;i++){  
            set.add(tmp[i]);  
        }  
        String[] arrayResult = (String[]) set.toArray(new String[set.size()]);  
        
        int length = arrayResult.length;
        
        if( length>10 ){
        	length = 10;//最多显示前10条数据
        }
      
       
        List<ProductSupplierTrend> listproductSupplierTrend=new ArrayList<ProductSupplierTrend>();
        
        for (int i = 0; i < length; i++) {
           // System.out.println(a[i]); 
            
            productParam.setShangJiaId(Integer.parseInt(arrayResult[i]));
          //先获取公司，也就是采购商/供应商
            ProductSupplierTrend  productSupplierTrend = productService.findBusinessTrendName(productParam);
        //  System.out.println("listBusinessTrendInfo"+listBusinessTrendInfo);
         
//          for (ProductSupplierTrend productSupplierTrend : listBusinessTrendInfo) {
              List<Trend> listBusinessTrendInfo2 = productService.findBusinessTrendInfo2(productParam);
              productSupplierTrend.setTrends(listBusinessTrendInfo2);
              
              listproductSupplierTrend.add(productSupplierTrend);
//          }
          
        }
       
       
        jsonStr = ResponseResult.createSuccess(listproductSupplierTrend).toJson();
        } catch (Exception e) {
            
            logger.error("详情页 供应商/采购商趋势："+e);
        }
        return jsonStr;
    }
    
    /**
     * 
     * 功能描述：合作伙伴关系图<br/>
     * URL ： /findRelationGraph.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findRelationGraph.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findRelationGraph(@RequestBody ProductParam productParam){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
    	// 代表采购商或供应商id
        Integer shangJiaId = productParam.getShangJiaId();
        if (shangJiaId==null) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
       
        String jsonStr=null;
        try {
            //获得点击的该供应商或采购商
            RelationGraph listRelationGraph = new RelationGraph();
            List<RelationGraph> listRelationGraph1 = new ArrayList();
            
            System.out.println(productParam.getShangJiaId()+"---"+productParam.getProKey());
            
           if(productParam.getProKey()!=null&&(!"".equals(productParam.getProKey()))){
        	   listRelationGraph= SearchNewService.findRelationGraph(productParam);
           }else{
        	   productService.findRelationGraph(productParam);
        	   productParam.setShangJiaId(listRelationGraph.getId());
        	   listRelationGraph1 = productService.findRelationGraph1(productParam);
        	   
               for (RelationGraph productSupplierTrend2 : listRelationGraph1) {
                   
                   List<RelationGraph> aaGraphs=new ArrayList<RelationGraph>();
                   
                   productParam.setShangJiaId( productSupplierTrend2.getId()); 
                   List<RelationGraph> listRelationGraph2 = productService.findRelationGraph1(productParam);
                   for (RelationGraph relationGraph : listRelationGraph2) {
                       if (relationGraph.getId()!=listRelationGraph.getId()) {
                           aaGraphs.add(relationGraph);
                       }
                   }
                   
                   
                   for (RelationGraph relationGraph1 : aaGraphs) {
                       
                       List<RelationGraph> aaGraphs1=new ArrayList<RelationGraph>();
                       
                       productParam.setShangJiaId(relationGraph1.getId()); 
                       //三级
                       List<RelationGraph> listRelationGraph3 = productService.findRelationGraph1(productParam); 
                       for (RelationGraph relationGraph3 : listRelationGraph3) {
                           if (relationGraph3.getId()!=productSupplierTrend2.getId()) {
                               aaGraphs1.add(relationGraph3);
                           }
                       }
                       relationGraph1.setGraph(aaGraphs1);                                       
                   }
                   
                   productSupplierTrend2.setGraph(aaGraphs);
                   
               }
               listRelationGraph.setGraph(listRelationGraph1);
               
           }
                //获得一级的
                
                
                
                /* for (RelationGraph productSupplierTrend2 : listRelationGraph1) {
                productParam.setShangJiaId(productSupplierTrend2.getId());
              //获得二级
                List<RelationGraph> listRelationGraph2 = productService.findRelationGraph2(productParam);
                
                for (RelationGraph relationGraph3 : listRelationGraph2) {
                    productParam.setShangJiaId(relationGraph3.getId());
                    
                    List<RelationGraph> listRelationGraph3 = productService.findRelationGraph3(productParam);
                    
                    relationGraph3.setGraph(listRelationGraph3);
                }
                
                productSupplierTrend2.setGraph(listRelationGraph2);*/
                
                //获得二级的
                /* List<RelationGraph> listRelationGraph2 = productService.findRelationGraph2(productParam);
                for (RelationGraph relationGraph : listRelationGraph2) {
                        productParam.setShangJiaId(relationGraph.getId());
                        List<RelationGraph> listRelationGraph3 = productService.findRelationGraph3(productParam);
                        relationGraph.setGraph(listRelationGraph3);
                    }
                    productSupplierTrend2.setGraph(listRelationGraph2);
                    
                    
                for (int i = 0; i < listRelationGraph2.size(); i++) {
                    if (listRelationGraph2.get(i).getId()==listRelationGraph.getId()) {
                        listRelationGraph2.remove(i);
                    }
                    
                }
                productSupplierTrend2.setGraph(listRelationGraph2);
                for (RelationGraph relationGraph : listRelationGraph2) {
                    productParam.setShangJiaId(relationGraph.getId());
                    List<RelationGraph> listRelationGraph3 = productService.findRelationGraph3(productParam);
                    relationGraph.setGraph(listRelationGraph3);
                    
                }*/
                    
                
                //获得二级
                 
               
           
            jsonStr = ResponseResult.createSuccess(listRelationGraph).toJson();
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("合作伙伴关系图"+jsonStr);
        }
        return jsonStr;
    }
    
    /**
     * 
     * 功能描述：近一年的交易历史<br/>
     * URL ： /findRecentTransaction.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findRecentTransaction.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findRecentTransaction(@RequestBody ProductParam productParam){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
        // 代表采购商或供应商id
        Integer shangJiaId = productParam.getShangJiaId();
        if (shangJiaId==null) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
        String orderBy = " order by count desc";
        if(productParam.getCountBy()!=null){
        	if(productParam.getCountBy() == 0){
        		orderBy = " order by count desc";
        	}else{
        		orderBy = " order by count";
        	}
        }
        if(productParam.getContainerBy()!=null){
        	if(productParam.getContainerBy() == 0){
        		orderBy = " order by volume desc";
        	}else{
        		orderBy = " order by volume";
        	}
        }
        if(productParam.getWeightBy()!=null){
        	if(productParam.getWeightBy() == 0){
        		orderBy = " order by weight desc";
        	}else{
        		orderBy = " order by weight";
        	}
        }
        productParam.setOrderby(orderBy);
        
       
        String jsonStr=null;
        try {
        	if(productParam.getPageNo()!=null && productParam.getPageSize() !=null){
                productParam.setPageNo((productParam.getPageNo()-1)*productParam.getPageSize());
                
            }else{ //默认
               productParam.setPageNo(0);
               productParam.setPageSize(10);
            }
        	 List<Map<String,Object>> listRecentTransaction = new ArrayList();
        	Integer total = productService.findRecentTransactionCount(productParam);
        	 if(productParam.getProKey()!=null&&(!"".equals(productParam.getProKey()))){
        		 listRecentTransaction = SearchNewService.queryProportionByProKey(productParam, 3);
        	 }else{
        		 listRecentTransaction = productService.findRecentTransaction(productParam);
        	 }
           
            jsonStr=ResponseResult.createSuccess(listRecentTransaction,total).toJson();
        } catch (Exception e) {
            
            logger.error("近一年的交易历史 :"+jsonStr);
        }
        return jsonStr;
    }
    /**
     * 
     * 功能描述：与xxx供应商的交易情况<br/>
     * URL ： /findTransactionInfo.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findTransactionInfo.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findTransactionInfo(@RequestBody ProductParam productParam){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
        // 代表采购商或供应商id
    	//System.out.println(productParam.getProKey().toString()+"tttttttt");
        Integer shangJiaId = productParam.getShangJiaId();
        if (shangJiaId==null) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
       
        String jsonStr=null;
        try {
        	if(productParam.getPageNo()!=null && productParam.getPageSize() !=null){
                productParam.setPageNo((productParam.getPageNo()-1)*productParam.getPageSize());
                
            }else{ //默认
               productParam.setPageNo(0);
               productParam.setPageSize(10);
            }
        	Integer total =0;
        	 List<Map<String, Object>> listTransactionInfo = new ArrayList();
        	
        	 System.out.println("aaaaa"+productParam.getProKey()!=null&&(!"".equals(productParam.getProKey())));
            if(productParam.getProKey()!=null&&(!"".equals(productParam.getProKey()))){
            	listTransactionInfo= SearchNewService.queryProportionByProKey(productParam,1);
            	total = listTransactionInfo.size();
            	listTransactionInfo=listTransactionInfo.subList(productParam.getPageSize()*productParam.getPageNo(), productParam.getPageSize()*productParam.getPageNo()+10);
            }else{
            	total = productService.findTransactionInfoCount(productParam);
            	listTransactionInfo= productService.findTransactionInfo(productParam);
            }
           
            jsonStr = ResponseResult.createSuccess(listTransactionInfo,total).toJson();
        } catch (Exception e) {
            
            logger.error("与xxx供应商的交易情况"+jsonStr);
        }
        return jsonStr;
    }
    
    /**
     * 
     * 功能描述：竞争对手进出口趋势图<br/>
     * URL ：/findCompete.php <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月22日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findCompete.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findCompete(@RequestBody ProductParam productParam){
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
     // 代表采购商或供应商id
        Integer shangJiaId = productParam.getShangJiaId();
        if (shangJiaId==null) {//必传字段，不可为空
            return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        }
       
        String jsonStr=null;
        try {
            //获取相关的竞争商家
           List<ProductSupplierTrend> listCompete = productService.findCompete(productParam);
           for (ProductSupplierTrend productSupplierTrend : listCompete) {
               productParam.setShangJiaId(productSupplierTrend.getId());
               //获取相关的时间段趋势
               List<Trend> listBusinessTrendInfo2 = productService.findBusinessTrendInfo2(productParam);
               productSupplierTrend.setTrends(listBusinessTrendInfo2);
           }
           
           jsonStr = ResponseResult.createSuccess(listCompete).toJson();
           
        } catch (Exception e) {
            logger.error("竞争对手进出口趋势图"+jsonStr);
        }
        return jsonStr;
    } 
    
    /**
     * 
     * 功能描述：核心采购商/供应商地区分布图<br/>
     * URL ： /findbusinessDistribution.php<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月23日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findbusinessDistribution.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findbusinessDistribution(@RequestBody ProductParam productParam){
//    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
        String jsonStr=null;
        try {
            jsonStr = productService.findbusinessDistribution(productParam);
            
        } catch (Exception e) {
           
            logger.error("核心采购商/供应商地区分布图  失败:"+e);
        }
        return jsonStr;
    }
    
    /**
     * 
     * 功能描述：交易清单<br/>
     * URL ：/findTransactionDetailed <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年3月9日<br/>
     * @param productParam
     * @return
     */
    @RequestMapping(value="/findTransactionDetailed" , method=RequestMethod.POST , produces=Constants.JSON)
    @ResponseBody
    public String findTransactionDetailed(@RequestBody ProductParam productParam) {
       
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
        if(productParam.getPageNo()!=null && productParam.getPageSize() !=null){
            productParam.setPageNo((productParam.getPageNo()-1)*productParam.getPageSize());
            
        }else{ //默认
            productParam.setPageNo(0);
           productParam.setPageSize(5);
        }
    
        String jsonStr=null;
        try {
            
            jsonStr = productService.findTransactionDetailed(productParam);
            
        } catch (Exception e) {
            
            logger.error("交易清单 失败"+e);
        }
        return jsonStr;
        
    }
    
    @RequestMapping(value="/findAllDetailByCompany.php" ,method=RequestMethod.POST, produces=Constants.JSON)
    @ResponseBody
    public String findAllDetailByCompany(@RequestBody ProductParam productParam){
    	 String jsonStr=null;
    	 return SearchNewService.queryAllDetail(productParam);
    	 /*try {
             
             jsonStr = productService.findTransactionDetailed(productParam);
             return ResponseResult.createSuccess(SearchNewService.queryAllDetail(productParam)).toJson();
             
         } catch (Exception e) {
        	 return ResponseResult.createFalied(Constants.OK_ERROR_CODE).toJson();
            
         }   */	
    	
    }
}
