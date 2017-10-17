package com.gls.db;

/**
 * 定义数据源插入、获取方法
 * @author ding
 * @since 2015年11月18日
 */
public class DBContextHolder {
	/** 
     * 线程threadlocal 
     */  
//    private ThreadLocal<String> contextHolder = new ThreadLocal<>();  
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();  
    //因为tomcat用了线程池, 有时会用的同一个线程， 区分 是否 已经set过参数 , 那么也就是说, 每次getDataSource()拿到此值的时候 都要重新清除此状态 
//    private static ThreadLocal<Boolean> isReset = new ThreadLocal<>();  
  
//    public static String DB_TYPE_RW = "master";  
//    public static String DB_TYPE_R = "slave";  
    
    public static final String DB_TYPE_CART = "cart";  
    public static final String DB_TYPE_B2C = "b2c"; 
  
    public static String getDataSource() {
    	/*String db = DB_TYPE_RW;		//默认值， 如果没有设置过的 ，就直接用主库吧,你懂滴
    	if (null != isReset.get() && isReset.get()) {
    		db = contextHolder.get();  
    		if (db == null) {
    			db = DB_TYPE_RW;// 默认是读写库  
    		}  
    	}
        //因为tomcat用了线程池, 有时会用的同一个线程， 区分 是否 已经set过参数 , 那么也就是说, 每次getDataSource()拿到此值的时候 都要重新清除此状态 
        isReset.remove();*/
    	String db = contextHolder.get();  
		if (db == null) {
			db = DB_TYPE_B2C;// 默认是读写库  
		}
		contextHolder.remove();	//fix 线程碰到同一条
        return db;  
    }
  
    /** 
     *  
     * 设置本线程的dbtype 
     *  
     * @param str 
     * @see [相关类/方法](可选) 
     * @since [产品/模块版本](可选) 
     */  
    public static void setDataSource(String str) {
    	//因为tomcat用了线程池, 有时会用的同一个线程， 区分 是否 已经set过参数 , 那么也就是说, 每次getDataSource()拿到此值的时候 都要重新清除此状态 
//        isReset.set(true);
        contextHolder.set(str);  
    }
  
    /** 
     * clearDBType 
     *  
     * @Title: clearDBType 
     * @Description: 清理连接类型 
     */  
    public static void clearDataSource() {
        contextHolder.remove(); 
    }  
    
    
  	//设置car db
  	public static void cart() {
  		setDataSource(DBContextHolder.DB_TYPE_CART);	
  	} 
  	
  	
  	
}
