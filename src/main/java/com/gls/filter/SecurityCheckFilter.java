package com.gls.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 过滤器,跨域处理
 * @author ding
 * @since 2016年5月6日
 */
public class SecurityCheckFilter extends OncePerRequestFilter {
	  
	private static Logger logger = LoggerFactory.getLogger(SecurityCheckFilter.class);
	
	public static final String [] ALLOW_LIST = {"www.xxx.com"};//授权可以跨域访问域名
	
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException { 

//		String referer = request.getHeader("Referer");
//		if(null != referer){
//			//根据.com截取授权域名
//			String refererUrl = referer.substring(0,referer.indexOf(".com")+4);//结果：http://www.xiaomeihome.com		
//			logger.info("跨域访问requestUrl:"+refererUrl);
//			for (int i = 0; i < ALLOW_LIST.length; i++) {
//				//允许访问了
//				if(refererUrl.contains(ALLOW_LIST[i])){
//					response.addHeader("Access-Control-Allow-Origin", ALLOW_LIST[i]);//动态设置允许跨域域名
//				}
//			}
//		}
		//测试环境其它环境，允许跨域访问 
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With,Content-Type");

		//转发给其他Filter
		filterChain.doFilter(request, response);
	}

}
