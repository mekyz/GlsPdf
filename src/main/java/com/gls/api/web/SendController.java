package com.gls.api.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gls.api.service.PhoneipService;
import com.gls.api.service.SendService;
import com.gls.pojo.PhoneMessageLogs;
import com.gls.pojo.PhoneMessageUsers;
import com.gls.util.Constants;

@Controller
public class SendController {

	@Autowired
	private SendService sendService;
	
	@Autowired
	private PhoneipService phoneipService;
	/**
	 * 导入物流列表
	 * @param response
	 * @param session
	 * @throws Exception 
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importUser(@RequestParam("files") CommonsMultipartFile[] files, 
			HttpServletResponse response, HttpSession session) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map=sendService.insertImportOrders(files);
		return map;
		
	}
	/**
	 * 群发短信
	 * @since 2016年12月7日
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST, produces = Constants.JSON)
	@ResponseBody
	public Map<String, Object> Sms(@RequestBody PhoneMessageLogs phoneMessageLogs,HttpServletResponse response, HttpSession session) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String content=phoneMessageLogs.getContent();
		if(content==null||"".equals(content)){
			map.put("info", "信息不能为空");	
			map.put(Constants.ERROR_CODE,-1);
			return map;
		}
		List<PhoneMessageUsers> list = sendService.getMobilephone_List();
		List<String> l=new ArrayList<>();
		int i=0;
		for(PhoneMessageUsers phoneMessageUsers:list){
			StringBuffer sms=new StringBuffer();
			Long mobilephone=phoneMessageUsers.getMobilephone();
			String userName=phoneMessageUsers.getUserName();
			String gender=phoneMessageUsers.getGender();
			sms.append(userName.substring(0, 1));
			if("男".equals(gender)){
				sms.append("先生,您好!");
			}else{
				sms.append("女士,您好!");
			}
			sms.append(content);
			Map<String, Object> m=phoneipService.sendToPhone(mobilephone+"",sms.toString());
			if(!"0".equals(m.get("status")+"")){
				l.add(mobilephone+"消息发送失败");
				continue;
			}
			i++;
			phoneMessageLogs.setMobilephone(mobilephone);
			phoneMessageLogs.setContent(sms.toString());
			sendService.insertMessageLogs(phoneMessageLogs);
		}
		map.put(Constants.ERROR_MSG,l);		
		map.put(Constants.ERROR_CODE,0);		
		map.put("info","成功推送给"+i+"人");		
		return map;
	}
}
