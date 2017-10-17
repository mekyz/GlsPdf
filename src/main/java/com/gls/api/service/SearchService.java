package com.gls.api.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gls.pojo.request.QueryParam;
import com.gls.search.entity.ProductEntity;
import com.gls.search.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gls.dao.mapper.HomeMapper;
import com.gls.pojo.District;
import com.gls.pojo.FrankOrderBeen;
import com.gls.search.core.ServerTypeEnum;
import com.gls.search.core.SolrCloudServer;
import com.gls.search.entity.FrankOrderEntity;
import com.gls.search.entity.SupplierEntity;
import com.gls.util.Constants;

/**
 * 
 * 文  件  名:SearchService<br/>  
 * 文件描述:<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月6日<br/>
 * 修改内容:<br/>
 */
@Service
public class SearchService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HomeMapper homeMapper;
	 
	 /**
     * 通过 solr 创建所有商品索引<br/>
     * 功能描述：<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月6日<br/>
     * @return  
     */
    public String createGoodsIndexAll(){
        try {
            //创建商品索引（路径）
            SolrServer solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.WM_PEODUCT);
            //创建之前先删除所有索引(慎用)
            SolrUtils.deleteAll(solrServer);
            
            //商品对象
            List<ProductEntity> goodsList = homeMapper.getProList();
            
            SolrUtils.addOrUpdateDocs(goodsList, solrServer);
            
            return ResponseResult.createSuccess("创建索引成功").toJson();
        } catch (Exception e) {
            logger.error("Exception: LucenModuleService createGoodsIndexAll  " + e.getMessage());
            return "创建索引失败";
        }
    }
    
    

    
    
    /**
     * 通过查询条件，使用solr查询出所有的商品<br/>
     * 功能描述：<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月6日<br/>
     * @param param
     * @return
     */
    public String getSolrGoodsList(Map<String,Object> map){
        
        SolrServer solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.WM_PEODUCT);
        // 获取分页信息
        Page<ProductEntity> page = new Page<>();
        page.setCurPage((int)map.get("pageNo"));
        page.setPageSize((int)map.get("pageSize")); 
        // 创建组合条件串  
        StringBuilder params = new StringBuilder();  
        // 组合商品条件  
        if(!"".equals(map.get("search_keywords")) && map.get("search_keywords")!=null) {  
            params.append("search_keywords:" + map.get("search_keywords"));
        }  
        try {
	        // 获取solr查询到的数据
	        Page<ProductEntity> goodsList = SolrUtils.queryByPage(page, params.toString(), solrServer, ProductEntity.class,null);
	        ResponseListResult createSuccess = ResponseListResult.createSuccess(goodsList.getObjects());
			createSuccess.setTotal(goodsList.getCounts());
            return createSuccess.toJson();
            
        } catch (Exception e) {
            logger.error("Exception: SearchService getLucenGoodsList  " + e.getMessage());
            return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
        }
    }

    /**
     * 功能描述：根据搜索条件查找相应的提单<br/>
     * 创建人: willian<br/>
     * 创建日期:2017年6月23日<br/>
     * @param param
     * @return
     */
    public String getFrankOrderList(QueryParam param) throws ParseException {


        //查询提单
        SolrServer solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.FRANK_ORDER);

        //封装分页
        Page<FrankOrderEntity> page = new Page<>();
        page.setCurPage(param.getPageNo()==null?1:param.getPageNo());
        page.setPageSize(param.getPageSize()==null?20:param.getPageSize()>20?20:param.getPageSize());
        StringBuilder params = new StringBuilder();
        //组合查询条件
        if (StringUtils.isNotBlank(param.getMudi_port())) {
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("by_mudiPort:").append(param.getMudi_port());
        }
        if (StringUtils.isNotBlank(param.getSupplier())) {
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("by_supplier:").append(param.getSupplier());
        }
        if (StringUtils.isNotBlank(param.getBuyer())) {
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("by_buniess:").append(param.getBuyer());
        }
        if (StringUtils.isNotBlank(param.getOriginalCountry())){
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("by_orginierCountry:").append(param.getOriginalCountry());
        }
        if (StringUtils.isNotBlank(param.getCountry())){
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("by_buniessAddress:\"").append(param.getCountry()).append("\"");
        }
        if (StringUtils.isNotBlank(param.getProductDesc())){
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("pro_desc:").append(param.getProductDesc());
        }if (StringUtils.isNotBlank(param.getStartDate()) && StringUtils.isNotBlank(param.getEndDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_date = sdf.parse(param.getStartDate());
            Date end_date = sdf.parse(param.getEndDate());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            String temp=format.format(start_date);
            String end_temp=format.format(end_date);
            int end_index=temp.indexOf("+");
            int date_end=end_temp.indexOf("+");
            String start=temp.substring(0,end_index)+"Z";
            String end=temp.substring(0,date_end)+"Z";
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("frankTime:[").append(start).append(" TO ").append(end).append("]");
        }else if (StringUtils.isNotBlank(param.getStartDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_date = sdf.parse(param.getStartDate());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            String temp=format.format(start_date);
            int end=temp.indexOf("+");
            String start=temp.substring(0,end)+"Z";
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("frankTime:[").append(start).append(" TO *]");
        }else if (StringUtils.isNotBlank(param.getEndDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date end_date = sdf.parse(param.getEndDate());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            String temp=format.format(end_date);
            int end=temp.indexOf("+");
            String UTC_date=temp.substring(0,end)+"Z";
            if (StringUtils.isNotBlank(params.toString())) params.append(" AND ");
            params.append("frankTime:[ *").append(" TO ").append(end_date).append("]");
        }
        //frankTime[2016-05-22T16:00:00Z TO *]
        try {
            Page<FrankOrderEntity> frank_order_list =  SolrUtils.queryByPage(page,params.toString(),solrServer,FrankOrderEntity.class,null);
            ResponseListResult success = ResponseListResult.createSuccess(frank_order_list.getObjects());
            success.setTotal(frank_order_list.getCounts());
            return success.toJson();
        }catch (Exception e) {
            logger.error("Exception: SearchService getLucenFrankOrderList  " + e.getMessage());
            return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
        }
    }

    /**
     * 
     * 功能描述：获取所有供应商列表<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月30日<br/>
     * @param map
     * @return
     */
    public String getBuyerList(String param){
        
        SolrServer solrServer = null;
        JSONObject object = JSONObject.parseObject(param);
        if(object.getInteger("type")==0){
            solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.BUSINIESS);
        }else if(object.getInteger("type")==1){
            solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.SUPPLIER);
        }else{
            //其他。。。
        }
        // 获取分页信息
        Page<SupplierEntity> page = new Page<SupplierEntity>();
        page.setCurPage(object.getInteger("pageNo")==null?1:object.getInteger("pageNo"));
        page.setPageSize(object.getInteger("pageSize")==null?20:object.getInteger("pageSize")>20?20:object.getInteger("pageSize")); 
        // 创建组合条件串  
        StringBuilder params = new StringBuilder();  
        // 组合商品条件  

        if(!"".equals(object.get("search_keywords")) && object.get("search_keywords")!=null && !"".equals(object.get("search_pro")) && object.get("search_pro")!=null){
            params.append("search_pro:\"" + object.get("search_pro") + "\" OR \"" + object.get("search_keywords").toString().replace(" ", "") + "\"" + //有问题
                    " AND search_keywords:\"" + object.get("search_keywords")+"\"");
        }else{
            if(!"".equals(object.get("search_keywords")) && object.get("search_keywords")!=null) {
                params.append("search_keywords:\"" + object.get("search_keywords")+"\" OR search_keywords:\"" + object.get("search_keywords").toString().replace(" ","")+"\"");
            }
            if(!"".equals(object.get("search_pro")) && object.get("search_pro")!=null) {
                params.append("search_pro:\"" + object.get("search_pro")+"\" OR search_pro:\"" + object.get("search_pro").toString().replace(" ","")+"\"");
            }
        }
        try {
            // 获取solr查询到的数据
            Page<SupplierEntity> goodsList = SolrUtils.queryByPage(page, params.toString(), solrServer, SupplierEntity.class,param);
            ResponseListResult createSuccess = ResponseListResult.createSuccess(goodsList.getObjects());
            createSuccess.setTotal(goodsList.getCounts());
            return createSuccess.toJson();
            
        } catch (Exception e) {
            logger.error("Exception: SearchService getLucenGoodsList  " + e.getMessage());
            return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
        }
    }
    

    
    /**
     * 
     * 功能描述：根据条件获取提单信息<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2011年2月8日<br/>
     * @param map
     * @return
     */
    public String getSolrFranklyById(String param) {
        
        JSONObject object = JSONObject.parseObject(param);
        
        //SolrServer solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.FRANK_ORDER);
        // 创建组合条件串
        //StringBuilder params = new StringBuilder();
        // 组合商品条件
       /* if (object.getBigInteger("orderId") != null) {
            params.append("by_orderId:" + object.getBigInteger("orderId"));
        }*/
        try {
        	if(object.getBigInteger("orderId") == null){
        		return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        	}
            // 获取solr查询到的数据
        	//FrankOrderEntity order = SolrUtils.queryById(params.toString(), FrankOrderEntity.class, solrServer);
        	BigInteger orderId = object.getBigInteger("orderId");
        	FrankOrderBeen order = homeMapper.getFrankOrBeen(orderId);
            return ResponseResult.createSuccess(order).toJson();
        } catch (Exception e) {
            logger.error("Exception: SearchService getSolrGoodsByQuery  " + e.getMessage());
            return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
        }
        
    }
    
    
    /**
     * 
     * 功能描述：获取商家数量<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2017年3月3日<br/>
     * @param map
     * @return
     */
    public String  getBuyerListTest(Map<String,Object> map){
        try {
        	
        	District district = homeMapper.getDistrictById(Integer.parseInt(String.valueOf(map.get("did"))));
        	if(district == null){
        		return ResponseResult.createFalied(Constants.ERROR_VALIDATE_CODE).toJson();
        	}
        	map.put("dlevel", district.getLevel());
            List<SupplierEntity> bList = homeMapper.getBuyerList(map);
            ResponseListResult rs = ResponseListResult.createSuccess(bList);
            rs.setTotal(homeMapper.getBuyerListCount(map));
            return rs.toJson();
        } catch (Exception e) {
            logger.error("Exception: SearchService getBuyerListTest  " + e.getMessage());
            return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
        }
        
    }
    
    
    
    
    
	
}
