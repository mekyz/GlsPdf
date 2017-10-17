package com.gls.api.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gls.api.service.TestService;
import com.gls.pojo.Users;
import com.gls.util.Constants;
import com.gls.util.Loggers;

@Controller
public class TestController {
	
	@Autowired
	private TestService testService;
	/**
	 * <b style="color:blue">test</b>
	 * @throws JSONException 
	 * @see Users
	 */
	@RequestMapping(value = "/testImport", method = RequestMethod.GET)
	@ResponseBody
	public Map test(HttpServletRequest request) throws JSONException {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			//Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		testService.handleData();
		map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}

}
