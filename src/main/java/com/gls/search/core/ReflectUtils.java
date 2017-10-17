/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：深圳市慧众云商科技有限公司
 * --------------------------------------------------------
 */

package com.gls.search.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 
 * 文  件  名:ReflectUtil<br/>  
 * 文件描述:<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月6日<br/>
 * 修改内容:<br/>
 */
public class ReflectUtils {
    
    public static Field[] getAllFields(Class<?> obj){
        
        return null;
    }
    public static Object invokeGetterMethod(Object model,Object property){
        
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        Object value = null;
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                     value = (String) m.invoke(model); // 调用getter方法获取属性值
                    /*if (value == null) {
                        m = model.getClass().getMethod("set"+name,String.class);
                        m.invoke(model, "");
                    }*/
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = model.getClass().getMethod("get" + name);
                     value = (Integer) m.invoke(model);
                    /*if (value == null) {
                        m = model.getClass().getMethod("set"+name,Integer.class);
                        m.invoke(model, 0);
                    }*/
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                     value = (Boolean) m.invoke(model);
                    /*if (value == null) {
                        m = model.getClass().getMethod("set"+name,Boolean.class);
                        m.invoke(model, false);
                    }*/
                }
                if (type.equals("class java.util.Date")) {
                    Method m = model.getClass().getMethod("get" + name);
                     value = (Date) m.invoke(model);
                   /* if (value == null) {
                        m = model.getClass().getMethod("set"+name,Date.class);
                        m.invoke(model, new Date());
                    }*/
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    
}
