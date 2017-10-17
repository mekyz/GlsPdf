package com.gls.search.util;

import java.text.NumberFormat;

/**
 * JSON格式转换类
 * 
 * @author 詹昌高
 * @version 2016-12-7
 */
public class ShuzhiChangeUtil {
	
	public static int dubleToSting(Double d){
	    //主要是针对带有字母的转换
	    NumberFormat nf = NumberFormat.getInstance();   
        nf.setGroupingUsed(false);  
        Integer value = Integer.parseInt(nf.format(d)) ;
        return value;
	}

}
