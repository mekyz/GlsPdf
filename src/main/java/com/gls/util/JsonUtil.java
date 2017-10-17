package com.gls.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class JsonUtil {

	/**
	 * 由于springmvc 3 ,4 提供的这个功能好有风险 ，有时候可以，有时候不可以，木有时间查，  自己写吧 
	 * list 去除null值
	 * @param list
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> List<LinkedHashMap<String, Object>> listToJson(List<T> list) throws IllegalArgumentException, IllegalAccessException, JsonParseException, JsonMappingException, IOException {
		String resultList = "[";
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				resultList += objectToJson(list.get(i));
			} else {
				resultList = resultList + "," + objectToJson(list.get(i));
			}
		}
		resultList += "]";
		//再转换成List格式
		ObjectMapper objectMapper = new ObjectMapper();
		List<LinkedHashMap<String, Object>> Tlist = objectMapper.readValue(resultList, List.class);
        return Tlist;
	}
	
	/**
	 * 单个对象 去除null值
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String objectToJson(Object obj) throws IllegalArgumentException, IllegalAccessException{
        if(obj == null){
            return "null";
        }
        String jsonStr = "{";
        Class<?> cla = obj.getClass();
        Field fields[] = cla.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
        	Field field = fields[i];
        	field.setAccessible(true);
        	
        	/*if (field.getName().equals(Constants.JSON_MAP_SERIALVERSIONUID)) {
        		continue;
        	}*/
        	
            if(field.getType() == long.class){
            	if (i == 0) {
            		jsonStr += "\""+field.getName()+"\":"+field.getLong(obj);  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+field.getLong(obj);  
            	}
            }else if(field.getType() == double.class){
        		if (i == 0) {
        			jsonStr += field.getName()+"\":"+field.getDouble(obj);  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+field.getDouble(obj);  
            	}
            }else if(field.getType() == float.class){
        		if (i == 0) {
        			jsonStr += "\""+field.getName()+"\":"+field.getFloat(obj);  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+field.getFloat(obj);  
            	}
            }else if(field.getType() == int.class){
        		if (i == 0) {
        			jsonStr += "\""+field.getName()+"\":"+field.getInt(obj);  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+field.getInt(obj);  
            	}
            }else if(field.getType() == boolean.class){
        		if (i == 0) {
        			jsonStr += "\""+field.getName()+"\":"+field.getBoolean(obj);  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+field.getBoolean(obj);  
            	}
            } else if( (field.getType() == Integer.class
            		|| field.getType() == Boolean.class  
                    || field.getType() == Double.class
                    || field.getType() == Float.class                     
//                    || field.getType() == Timestamp.class                     
                    || field.getType() == Long.class) && null!=field.get(obj)) {	//去除null	
        		if (i == 0) {
        			jsonStr += "\""+field.getName()+"\":"+field.get(obj);  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+field.get(obj);  
            	}
            } else if( (field.getType() == String.class) && null!=field.get(obj)) {	//String 去除null	
        		if (i == 0) {
        			jsonStr += "\""+field.getName()+"\":"+ "\"" + field.get(obj) + "\"";  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+ "\"" + field.get(obj) + "\"";  
            	}
            } else if( (field.getType() == Timestamp.class) && null!=field.get(obj)) {	//Timestamp 去除null, 这里 show不出来 , nullpoint	
        		/*if (i == 0) {
        			jsonStr += "\""+field.getName()+"\":"+ "\"" + field.get(obj) + "\"";  
            	} else {
            		jsonStr += "," + "\""+field.getName()+"\":"+ "\"" + field.getLong(obj) + "\"";  
            	}*/
            }
		}
//        jsonStr = jsonStr.substring(0,jsonStr.length()-1);
        jsonStr += "}";
            return jsonStr;
    }
	
}
