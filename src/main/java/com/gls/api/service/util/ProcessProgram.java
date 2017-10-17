package com.gls.api.service.util;

import java.util.Arrays;

public class ProcessProgram {

	public static String processListCompany(String listCompany){
		
		String jsonStr="";
		
		try{
			System.out.print(listCompany);
			if(listCompany.contains("resultList")){
				if(listCompany.indexOf("resultList:null") == -1){

				//获取类Json文件
				int startIndex = listCompany.indexOf("resultList:[");
				int endIndex = listCompany.indexOf("],totalCount");
				String jsonBefore = listCompany.substring(startIndex,endIndex);
				jsonBefore=  jsonBefore.replace("resultList:[","").replace("\"", "").replace("\\", "").replace(", ", " ");


				//转换Json
				jsonStr = getJson(jsonBefore);

				}
				return jsonStr;
			}else{
				System.out.println("当前数据并无公司信息");
			}
		}catch(Exception ex){
			
			System.out.println("Exception: 解析获取到的公司列表Json失败 " + ex);
			throw ex;
		}
		
		return jsonStr;
	}
	
	public static String getJson(String jsonStr){
	
		String result = "";
		
		try{
			if(!jsonStr.equals("")){
					
				jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
				String[] strs = jsonStr.split(",");
				StringBuffer stringBuffer = new StringBuffer("{");
				
				for (int i = 0; i < strs.length; i++) {
				    stringBuffer.append("\""
				          + strs[i].substring(0, strs[i].indexOf(":")) + "\":\""
				          + strs[i].substring(strs[i].indexOf(":") + 1) + "\",");
				            }
				stringBuffer.replace(stringBuffer.length() - 1,stringBuffer.length(), "}");
	
				result = stringBuffer.toString();
				result = "["+result.replace("}\",\"{", "\"},{\"")+"]";
				
				//此方法在JDK1.8版本之后才能使用
				/*result = Arrays.stream(jsonStr.substring(1, jsonStr.length() - 1).split(","))
		                .map(v -> "\"" + v.replaceFirst(":", "\":\"") + "\"")
		                .reduce("{", (a, b) -> a + b + ",")
		                .toString();
				result = result.substring(0, result.length() - 1) + "}";
				result = "["+result.replace("}\",\"{", "\"},{\"")+"]";*/

				System.out.println(result);

			}
			
			return result;
			
		}catch(Exception ex){
			
			System.out.println("Exception: Get Json Failed !" + ex.getMessage());
			
			System.out.println("获取Json数据失败！");
		}
		return result;
	}
}
