package com.gls.api.service.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

public class EmployeeListServer {

public static String searchPersonList(Map<String,String> cookieMapNew,String companyUrl){
		
		String url = "https://connect.data.com/dwr/call/plaincall/SearchDWR.findContacts.dwr";
		
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("callCount","1");
		dataMap.put("nextReverseAjaxIndex","0");
		dataMap.put("c0-scriptName","SearchDWR");
		dataMap.put("c0-methodName","findContacts"); 
		dataMap.put("c0-id","0");
		dataMap.put("c0-param0","string:%7B%22filters%22%3A%7B%22companies%22%3A%5B%22"+companyUrl+"%22%5D%7D%2C%22actionsOnColumns%22%3A%7B%22companyName%22%3A%7B%22sort%22%3A%22asc%22%7D%7D%2C%22totalRecordsOnPage%22%3A50%7D");
		dataMap.put("batchId","0");
		dataMap.put("instanceId","0");
		dataMap.put("page","%2Fsearch");
		dataMap.put("scriptSessionId",cookieMapNew.get("DWRSESSIONID")+"/abcrtruyruyryuyreu6ru");
		
		String jsonStr = "";
		
		try {
		
			String html = Jsoup.connect(url).cookies(cookieMapNew).ignoreContentType(true).method(Method.POST).data(dataMap).execute().body();
		
			jsonStr = ProcessProgram.processListCompany(html);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return jsonStr;
		
	}
}
