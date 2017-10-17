package com.gls.api.service.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

public class CompanyListServer {
	
	public static String getCompanyList(Map<String,String> cookieMapNew,String companyName) throws IOException{
		
			String url = "https://connect.data.com/dwr/call/plaincall/SearchDWR.singleBoxSearch.dwr";

			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("callCount","1");
			dataMap.put("nextReverseAjaxIndex","0");
			dataMap.put("c0-scriptName","SearchDWR");
			dataMap.put("c0-methodName","singleBoxSearch"); 
			dataMap.put("c0-id","0");
			dataMap.put("c0-param0","string:"+companyName);
			dataMap.put("batchId","0");
			dataMap.put("instanceId","0");
			dataMap.put("page","%2Fsearch");
			dataMap.put("scriptSessionId",cookieMapNew.get("DWRSESSIONID")+"/abcrtruyruyryuyreu6ru");
			
			String html = Jsoup.connect(url).cookies(cookieMapNew).ignoreContentType(true).method(Method.POST).data(dataMap).execute().body();
			
			String listCompany = ProcessProgram.processListCompany(html);

			return listCompany;
	}
	
	public static String getBeJson(String html){
		
		int startIndex = html.indexOf("(\"0\",\"0\",");
		int endIndex = html.indexOf("})();");
		
		String value = html.substring(startIndex,endIndex-3);
		value = value.replace("(\"0\",\"0\",", "");
		
		return value;
	}
}
