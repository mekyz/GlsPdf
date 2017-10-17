package com.gls.api.service.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class LoginDataCom {

	public static Map<String,String> autoLogin(String userName,String passWord) throws IOException{
		
		   Connection con = Jsoup.connect("https://connect.data.com/login");//获取连接  
	       con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");//配置模拟浏览器  
	       Response rs= con.execute();//获取响应  
	       Document d1=Jsoup.parse(rs.body());//转换为Dom树  
	       List<Element> et= d1.select("#command").select(".middle");//获取form表单，可以通过查看页面源码代码得知  
	          
	       Map<String, String> datas = new HashMap<String, String>(); //获取，cooking和表单属性，下面map存放post时的数据   
	       
	       for(Element e : et ){  
	           if(e.child(0).attr("name").equals("j_username")){  
	        	   e.attr("value",userName );//设置用户名  
	        	   //e.attr("value", "jas_tmp@126.com");//设置用户名  
	           }  
	           if(e.child(0).attr("name").equals("j_password")){  
	               e.attr("value",passWord); //设置用户密码  
	           }  
	           if(e.child(0).attr("name").length()>0){//排除空值表单属性  
	                 datas.put(e.child(0).attr("name"), e.attr("value"));    
	           } 
	       }
	       
	       /*加入token*/
	       Element token = d1.getElementById("CSRF_TOKEN");
	       String tokenName = token.attr("name");
	       String tokenValue = token.attr("value");
	       datas.put(tokenName, tokenValue);
	       
	       Connection con2=Jsoup.connect("https://connect.data.com/loginProcess");  
	       
	       con2.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");  
	      
	       //设置cookie和post上面的map数据  
	       Response login = con2.ignoreContentType(true)
	    		   .method(Method.POST)
	    		   .data(datas)
	    		   .cookies(rs.cookies())
	    		   .timeout(30*1000)
	    		   .execute();  
	       
	       //System.out.println("login.body() = " + login.body());
	       
	       //登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可  
	       Map<String, String> cookieMap = login.cookies(); 
	       cookieMap.put(tokenName, tokenValue);
	       
	       Map<String,String> getDwrId = getDwrId(cookieMap);
	       
	       return getDwrId;
		}
	public static Map<String,String> getDwrId(Map<String,String> cookieMap) throws IOException{
		
		String url = "https://connect.data.com/dwr/call/plaincall/SearchDWR.singleBoxSearch.dwr";
		
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("callCount","1"	);
		dataMap.put("c0-scriptName","__System");
		dataMap.put("c0-methodName","generateId");
		dataMap.put("c0-id","0");
		dataMap.put("batchId","0");
		dataMap.put("instanceId","0");
		dataMap.put("page","%2Fmax_blog_bas%2Findex.jsp");
		dataMap.put("scriptSessionId","");
		dataMap.put("windowName","");

		 try {  //避免请求过快，休眠500ms
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		String html = Jsoup.connect(url).cookies(cookieMap).ignoreContentType(true).method(Method.POST).data(dataMap).ignoreContentType(false).execute().body();
		
		String dwrValue = getDwrValue(html);
		
		cookieMap.put("DWRSESSIONID", dwrValue);
		
		return cookieMap;
	}
	
	public static String getDwrValue(String html){
		
		int startIndex = html.indexOf("(\"0\",\"0\",\"");
		int endIndex = html.indexOf("\");");
		
		String value = html.substring(startIndex,endIndex);
		value = value.replace("(\"0\",\"0\",\"", "");
		
		return value;
	}
}
