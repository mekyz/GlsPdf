package com.gls.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gls.api.service.PhoneipService;
import com.gls.util.CodeUtil;
import com.gls.util.Constants;
import com.gls.util.Loggers;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.InvalidRequestException;

public class ExceptionConstroller {

	@Autowired
	private PhoneipService phoneipService;
	//这里还没有试出 ，如果 phoneipService也挂 了的话，会不会 出现问题
	
	@ExceptionHandler
	@ResponseBody
    public Map APIException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(ex instanceof RedisConnectionFailureException) {		//redis 连接的异常
        	map.put(Constants.ERROR_CODE, Constants.REDIS_EXCEPTION_CODE);
        	map.put(Constants.ERROR_MSG, Constants.REDIS_EXCEPTION_MSG);
        	
        	String apiError = " RedisConnectionFailureException >>>> " + request.getRequestURI() + " X-Real-IP: " + CodeUtil.clientIp(request);
        	phoneipService.sendToPhone4APIAlert(apiError);
		} else if(ex instanceof IOException) {
        	Loggers.WEB_ERROR_LOGGER.error("IO Exception >>>> " + ex.getClass());
        	Loggers.WEB_ERROR_LOGGER.error("IO Exception Controller >>>> " + request.getRequestURI());
        	map.put(Constants.ERROR_CODE, Constants.IO_EXCEPTION_CODE);
        	map.put(Constants.ERROR_MSG, Constants.IO_EXCEPTION_MSG);
        	
        	String apiError = " IOException >>>> " + request.getRequestURI() + " X-Real-IP: " + CodeUtil.clientIp(request) + ex.getStackTrace()
        	+ ex.getMessage() + ex.getCause();
        	phoneipService.sendToPhone4APIAlert(apiError);
        	ex.printStackTrace();
        } else if(ex instanceof AuthenticationException) {
        	Loggers.WEB_ERROR_LOGGER.error("AuthenticationException >>>> " + ex.getClass());
        	Loggers.WEB_ERROR_LOGGER.error("AuthenticationException Controller >>>> " + request.getRequestURI());
        	map.put(Constants.ERROR_CODE, Constants.AUTHENTICATIONEXCEPTION_CODE);
        	map.put(Constants.ERROR_MSG, Constants.AUTHENTICATIONEXCEPTION_MSG);
        	
        	String apiError = " PingxxAuthenticationException >>>> " + request.getRequestURI() + " X-Real-IP: " + CodeUtil.clientIp(request) 
        	+ ex.getStackTrace()
        	+ ex.getMessage() + ex.getCause();
        	phoneipService.sendToPhone4APIAlert(apiError);
        	ex.printStackTrace();
        } else if(ex instanceof InvalidRequestException) {
        	Loggers.WEB_ERROR_LOGGER.error("InvalidRequestException >>>> " + ex.getClass());
        	Loggers.WEB_ERROR_LOGGER.error("InvalidRequestException Controller >>>> " + request.getRequestURI());
        	map.put(Constants.ERROR_CODE, Constants.INVALIDREQUESTEXCEPTION_CODE);
        	map.put(Constants.ERROR_MSG, Constants.INVALIDREQUESTEXCEPTION_MSG);
        	
        	String apiError = " PingxxInvalidRequestException >>>> " + request.getRequestURI() 
        			+ " X-Real-IP: " + CodeUtil.clientIp(request) + ex.getStackTrace()
        			+ ex.getMessage() + ex.getCause();
        	phoneipService.sendToPhone4APIAlert(apiError);
        	ex.printStackTrace();
        } else if(ex instanceof APIConnectionException) {
        	Loggers.WEB_ERROR_LOGGER.error("APIConnectionException >>>> " + ex.getClass());
        	Loggers.WEB_ERROR_LOGGER.error("APIConnectionException Controller >>>> " + request.getRequestURI());
        	map.put(Constants.ERROR_CODE, Constants.APICONNECTIONEXCEPTION_CODE);
        	map.put(Constants.ERROR_MSG, Constants.APICONNECTIONEXCEPTION_MSG);
        	
        	String apiError = " PingxxAPIConnectionException >>>> " + request.getRequestURI() + " X-Real-IP: " + CodeUtil.clientIp(request)
        	+ ex.getStackTrace() + ex.getMessage() + ex.getCause();
        	phoneipService.sendToPhone4APIAlert(apiError);
        	ex.printStackTrace();
        } else if(ex instanceof APIException) {
        	Loggers.WEB_ERROR_LOGGER.error("APIException >>>> " + ex.getClass());
        	Loggers.WEB_ERROR_LOGGER.error("APIException Controller >>>> " + request.getRequestURI());
        	map.put(Constants.ERROR_CODE, Constants.APIEXCEPTION_CODE);
        	map.put(Constants.ERROR_MSG, Constants.APIEXCEPTION_MSG);
        	
        	String apiError = " PingxxAPIException >>>> " + request.getRequestURI() + " X-Real-IP: " + CodeUtil.clientIp(request)
        	+ ex.getStackTrace() + ex.getMessage() + ex.getCause();
        	phoneipService.sendToPhone4APIAlert(apiError);
        	ex.printStackTrace();
        } else if(ex instanceof ServerException) {		//统一的service层的  数据udpate失败后的 异常 
        	Loggers.WEB_ERROR_LOGGER.error("ServerException >>>> " + ex.getClass());
        	Loggers.WEB_ERROR_LOGGER.error("ServerException Controller >>>> " + request.getRequestURI() + " X-Real-IP: " + CodeUtil.clientIp(request));
        	map.put(Constants.ERROR_CODE, Constants.SERVER_EXCEPTION_CODE);
        	if (((ServerException) ex).getInfo() != null) {
        		map.put(Constants.ERROR_MSG, ((ServerException) ex).getInfo());
        	} else {
        		map.put(Constants.ERROR_MSG, Constants.SERVER_EXCEPTION_MSG);
        	}
        } else {	//服务端不可告异常
        	Loggers.WEB_ERROR_LOGGER.error("Server Exception >>>> " + ex.getClass());
        	Loggers.WEB_ERROR_LOGGER.error("Server Exception Controller >>>> " + request.getRequestURI());
        	map.put(Constants.ERROR_CODE, Constants.SERVER_EXCEPTION_CODE);
        	map.put(Constants.ERROR_MSG, Constants.SERVER_EXCEPTION_MSG);
//        	String apiError = " Exception Controller >>>> " + request.getRequestURI() + " X-Real-IP: " + CodeUtil.clientIp(request);
//        	phoneipService.sendToPhone4APIAlert(apiError);
        }
		
//		if (null != ((ServerException) ex)) {
		if (ex instanceof ServerException) {
			
			if (((ServerException) ex).getInfo() != null) {
				Loggers.WEB_ERROR_LOGGER.error("Server Exception Controller >>>> " + request.getRequestURI() + "   " + ((ServerException) ex).getInfo());
        	}
			
//			ex.printStackTrace();
		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			ex.printStackTrace();
		}
		return map;
    }
	
}
