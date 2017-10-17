package com.gls.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.gls.util.Loggers;


/**
 * 重新获取数据源方法，自动查找datasource 
 * @author ding
 * @since 2015年11月18日
 */
public class DynamicDataSource extends AbstractRoutingDataSource {  

    /** 
     *  
     * override determineCurrentLookupKey 
     * <p> 
     * Title: determineCurrentLookupKey 
     * </p> 
     * <p> 
     * Description: 自动查找datasource 
     * </p> 
     *  
     * @return 
     */  
    @Override  
    protected Object determineCurrentLookupKey() {
    	String dataSource = DBContextHolder.getDataSource();
//    	if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
//    		Loggers.WEB_ERROR_LOGGER.debug("当前DataSource：" + dataSource);
//    	}
    	
    	if (DBContextHolder.DB_TYPE_CART.equals(dataSource)) {
    		Loggers.WEB_ERROR_LOGGER.debug(" DataSource：" + dataSource);
    	}
        return dataSource;  
    }
  
}  