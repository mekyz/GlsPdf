package com.gls.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @deprecated
 * @author Administrator
 *
 */
public class DBSourceUtil extends BasicDataSource {
	@Override  
    public synchronized void close() throws SQLException {
		//此方法 被调用时好像有bug , 为什么 还在用,就要close()? tomcat的Listener问题, 连接池 m leak的问题, 等 彻底优化
        DriverManager.deregisterDriver(DriverManager.getDriver(url));  
        if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(" DBSourceUtil > close > ");
		}
        super.close();  
    }
}
