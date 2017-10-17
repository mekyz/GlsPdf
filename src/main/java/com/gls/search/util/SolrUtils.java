

package com.gls.search.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.alibaba.fastjson.JSONObject;
import com.gls.search.core.SolrEnum;
import com.gls.search.core.SolrField;
/**
 * 
 * 文  件  名:SolrUtils<br/>  
 * 文件描述:<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月6日<br/>
 * 修改内容:<br/>
 */
public class SolrUtils {
    
    /** 
     * 为多个文档对象的，某些属性建立索引 
     * 
     * @date 
     * @param 
     * @param 
     * @param 
     */  
    public static <T> void addOrUpdateDocs(List<T> list, SolrServer solrServer) {        
        if(null == list || list.size() == 0 ) {  
            return;  
        }  
        try { 
            List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();  
            Field[] fields = list.get(0).getClass().getDeclaredFields();  
            for (T obj : list) {  
                SolrInputDocument doc = new SolrInputDocument();  
                for (Field field : fields) {
                   if(field.isAnnotationPresent(SolrField.class)){
                       SolrField solrField =   field.getAnnotation(SolrField.class);  
                       if(solrField.field().equals(SolrEnum.FIELD)){
                           String property = field.getName();
                           String uProperty = property.substring(0, 1).toUpperCase() + property.substring(1); // 将属性的首字符大写，方便构造get方法          
                           Method m = obj.getClass().getMethod("get" + uProperty);
                           if(!"id".equals(property) && solrField.key().equals(SolrEnum.KEY)){//entity主键
                               doc.addField("id",m.invoke(obj)); 
                           }
                           doc.addField(property, m.invoke(obj));    
                       }
                   }
                }  
                docs.add(doc);  
            }  
            solrServer.add(docs);  
            solrServer.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 建立单个索引 
     * 
     * @param <T> 
     * @date 
     * @param 
     * @param 
     */  
    public static <T> void addOrUpdateOneDoc(T obj, SolrServer solrServer) {  
        List<T> list = new ArrayList<T>();  
        list.add(obj);  
        addOrUpdateDocs(list, solrServer);  
    }  
    /** 
     * 根据id删除某条索引 
     *  
     * @date 
     * @param 
     */  
    public static void deleteById(String id, SolrServer solrServer) {  
        try {  
            solrServer.deleteById(id);  
            solrServer.commit();  
        } catch (Exception e) {  
           e.printStackTrace();  
        }  
    }  
    /** 
     * 删除全部商品索引,尽量不要使用 
     */  
    public static void deleteAll(SolrServer solrServer){  
        try {  
            solrServer.deleteByQuery("*:*");  
            solrServer.commit();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }    
    /**
     * 唯一主键 solr 库 id 和区域id查询  
     * @param id
     * @param entityClass
     * @param solrServer
     * @return
     */
    @SuppressWarnings("unchecked")  
    public static <T> T queryById(String queryString, Class<?> entityClass, SolrServer solrServer) {  
        T obj = null;  
        try {  
            obj = (T) entityClass.newInstance();   
	        SolrQuery query = new SolrQuery();  
	        query.setQuery(queryString);  
	        QueryResponse response = null;  
	        response = solrServer.query(query);  
	        SolrDocumentList docs = response.getResults();  
	        if(null == docs || docs.size() == 0) {  
	            return null;  
	        }  
	        SolrDocument doc = docs.get(0);  
	        Field[] fields = obj.getClass().getDeclaredFields();  
	        for (Field field : fields) {  
	            String propertyName = field.getName();
	            Object propertyValue =  doc.getFieldValue(propertyName); 
	          
	            Class<?> propertyClass = field.getType();  
	            String upperProperty = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1); // 将属性的首字符大写，方便构造set方法
	            if(propertyValue!=null){
	            	Method m = obj.getClass().getMethod("set" + upperProperty,propertyClass);
                    m.invoke(obj, propertyValue);
	            }
	        } 
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return obj;  
    }  
      
    /** 
     * solr获取的分页对象 
     *  
     * @param page 传进来的分页对象
     * @param queryString  solr的查询参数 web搜索页面的fa字段
     * @param solrServer solrServer对象
     * @param  entityClass 返回的结果对象 search包下的entity对象
     * @param param  是否需要将结果进行排序  不需要传null
     * @return 
     */
    @SuppressWarnings("unchecked")  
    public static <T> Page<T> queryByPage(Page<T> page,String queryString, SolrServer solrServer , Class<?> entityClass,String param) {  
    	
    	if(page==null || solrServer==null){
    		return null;
    	}
    	try { 
    	    SolrQuery solrQuery = new SolrQuery();
//    	    
//    	    solrQuery.setParam("hl", "true");
//    	    solrQuery.setParam("hl.fl", "sname,lastProductDesc,search_pro,search_keywords");  
//    	    solrQuery.setHighlightSimplePre("<font color=\"red\">");   
//    	    
//    	    solrQuery.setHighlightSimplePost("</font>"); 
    	    
    	    if(StringUtility.isNullOrEmpty(queryString)){
    	        solrQuery.setQuery("*:*");
    	    }else{
    	        solrQuery.setQuery(queryString);
    	    }
    	    JSONObject object = JSONObject.parseObject(param);
    	    if(object != null && object.getInteger("sort")!=null){
        	    if(object.getInteger("sort")==1){
        	        solrQuery.addSort(new SortClause("totalCount", ORDER.desc)); // 排序
        	    }else if(object.getInteger("sort")==-1){
        	        solrQuery.addSort(new SortClause("totalCount", ORDER.asc)); // 排序
        	    }else if(object.getInteger("sort")==2){
                    solrQuery.addSort(new SortClause("totalWeight", ORDER.desc)); // 排序
                }else if(object.getInteger("sort")==-2){
                    solrQuery.addSort(new SortClause("totalWeight", ORDER.asc)); // 排序
                }else if(object.getInteger("sort")==3){
                    solrQuery.addSort(new SortClause("totalVolume", ORDER.desc)); // 排序
                }else if(object.getInteger("sort")==-3){
                    solrQuery.addSort(new SortClause("totalVolume", ORDER.asc)); // 排序
                }else if(object.getInteger("sort")==4){
                    solrQuery.addSort(new SortClause("lastDate", ORDER.desc)); // 排序
                }else{
                    solrQuery.addSort(new SortClause("lastDate", ORDER.asc)); // 排序
                }
    	    }else{
    	    	//solrQuery.addSort(new SortClause("totalCount", ORDER.desc)); // 排序//设置默认排序
    	    }
    	    //设置默认搜素域
//    	    solrQuery.set("df", "search_keywords");
    	    //设置分页
    	    solrQuery.setStart((page.getCurPage() - 1) * page.getPageSize());
    	    solrQuery.setRows(page.getPageSize());
	        QueryResponse queryResponse = null;  
	        queryResponse = solrServer.query(solrQuery);   
	        SolrDocumentList docs = queryResponse.getResults();  
	        List<T> list = new ArrayList<>();  
	        for(SolrDocument doc : docs){  
	            T obj =  (T) entityClass.newInstance();  
	            Field[] fields = obj.getClass().getDeclaredFields();  
	            for (Field field : fields) {  
	                String propertyName = field.getName();  
	                Object propertyValue = doc.getFieldValue(propertyName);  
	                Class<?> propertyClass = field.getType();  
	                String upperProperty = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1); // 将属性的首字符大写，方便构造get方法
		            if(propertyValue!=null){
		             Method m = obj.getClass().getMethod("set" + upperProperty,propertyClass);
		             if(propertyName.equals("sname") && object.getString("search_keywords")!=null && !object.getString("search_keywords").equals("")){
		            	 String flValue = (String) propertyValue;
		            	 flValue = flValue.replace(object.getString("search_keywords").toUpperCase(), "<font color=red>"+object.getString("search_keywords").toUpperCase()+"</font>");
		            	 m.invoke(obj, flValue);
		             }else if(propertyName.equals("lastProductDesc") && object.getString("search_pro")!=null && !object.getString("search_pro").equals("")){
		            	 String flValue = (String) propertyValue;
		            	 flValue = flValue.replace(object.getString("search_pro").toUpperCase(), "<font color=red>"+object.getString("search_pro").toUpperCase()+"</font>");
		            	 m.invoke(obj, flValue);
		             } else{
		            	 m.invoke(obj, propertyValue);
		             }

		            } 
	            }  
	            list.add(obj);  
	        }  
	        int recordCount = list.size();
	        int pageCount = recordCount / page.getPageSize();
	        
	        long num = docs.getNumFound();  
	        
	        if (recordCount % page.getPageSize() > 0) {
	            pageCount++;
	        }
	        page.setCounts((int)num);  
	        page.setObjects(list); 
	        page.setPageCount(pageCount);
	        
    	} catch (Exception e) {  
            e.printStackTrace();  
        } 
        return page;  
    }
      
   /* *//** 
     * 优化solr索引 
     * 
     * @date 2015-8-31 上午12:02:49 
     * @param solrClient 
     *//*  
    public static void optimize(String collection, SolrServer solrServer) {  
        try {  
            solrServer.optimize();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  */
}  