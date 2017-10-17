package com.gls.api.web;

import java.net.URL;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gls.api.service.ComDetailService;
import com.gls.pojo.RequestObj;

/**
 * 公司详情相关Controller
 * @author Ding
 * @since 2017年3月6日
 */
@Controller
public class ComDetailController {
	
	@Autowired ComDetailService comDetailService;
	/**
	 * 跳转到公司详情，重定向到详情页面
	 * @since 2017年3月6日
	 * @param name 公司名字，完全匹配
	 * @param type 0为采购商，1未供应商
	 * @return 重定向URL
	 * @throws Exception
	 */
	@RequestMapping(value = "/toDetail", method = RequestMethod.GET)
	public String toDetail(RequestObj requestObj) throws Exception{
		
		requestObj.setName(URLDecoder.decode(requestObj.getName(),"UTF-8"));
		requestObj.setName(requestObj.getName().replace("&", "").replace(",", "")
				.replace(" ", "").replace(".", "").replace("LTD", "").replace("CO", "").replace("COMPANY", "")
				.replace("CORPORATION", "").replace("LCC", "").replace("L.L.C", "").replace("INC", "")
				.replace("CORP", "").replace("CP", "").replace("INCORPORATED", "").replace("(", "").replace(")", ""));
		
		//根据公司名字匹配公司ID,跳转到公司详情页面
		Integer id = comDetailService.getComIdByName(requestObj);
		if(id == null){
			return  "redirect:http://static.g2l-service.com/trade/image/404.html";
		}
		//测试id 80427
		return  "redirect:http://tradeweb.g2l-service.com/details.html?shangJiaId="+id+"&ietype="+requestObj.getType()+"&vwtype=0&regionid=0&comtype="+requestObj.getType();
	}
}
