package com.gls.db; 
import java.lang.annotation.ElementType;   
import java.lang.annotation.Retention;   
import java.lang.annotation.RetentionPolicy;   
import java.lang.annotation.Target;   

/**
 * 多数据源自定义注解
 * 使用：类或者方法前添加 DataSource("数据源")
 * @author ding
 * @since 2015年11月18日
 */

//自定义注解相关设置
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface DataSource {
    String value();
}

