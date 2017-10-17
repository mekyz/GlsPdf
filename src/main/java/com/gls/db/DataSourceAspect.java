package com.gls.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 *  多数据源自定义注解用到，支持class注解，method注解 
 *  作用在service层，切面注入的时候只会在service调用一次切换，需要使用多个查询的时候，有bug，需要手动set
 *  最终的解决方案是作用在mapper数据层
 * @author ding
 * @since 2015年11月18日
 */
public class DataSourceAspect {
	 public void before(JoinPoint point)
	    {
	        Object target = point.getTarget();
	        String method = point.getSignature().getName();

	        //Class<?>[] classz = target.getClass().getInterfaces();
	        Class<?> classz = target.getClass();//取到目标class

	        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
	                .getMethod().getParameterTypes();//方法参数
	        try {
	        	//class使用DataSource注解
	        	if(classz.isAnnotationPresent(DataSource.class)){
	        		DataSource data = classz.getAnnotation(DataSource.class);//get注解实例
	                DBContextHolder.setDataSource(data.value());
	        	}
	        	//method使用DataSource注解，set注解使用的dataSorce
	            Method m = classz.getMethod(method, parameterTypes);
	            if (m != null && m.isAnnotationPresent((Class<? extends Annotation>) DataSource.class)) {
	                DataSource data = m
	                        .getAnnotation(DataSource.class);
	                DBContextHolder.setDataSource(data.value());
	            }
	            
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	    }
}
