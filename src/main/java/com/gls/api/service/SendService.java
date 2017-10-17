package com.gls.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gls.api.service.redis.RedisService;
import com.gls.dao.mapper.PhoneMessageLogsMapper;
import com.gls.dao.mapper.PhoneMessageUsersMapper;
import com.gls.pojo.PhoneMessageLogs;
import com.gls.pojo.PhoneMessageUsers;
import com.gls.util.Constants;
import com.gls.util.ExcelUtil;

@Service
public class SendService extends RedisService {
	
	@Autowired
	private PhoneMessageUsersMapper phoneMessageUsersMapper;
	
	@Autowired
	private PhoneMessageLogsMapper phoneMessageLogsMapper;
	/**
	 * 导入电话
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String,Object> insertImportOrders(CommonsMultipartFile[] files) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<String[]> dataList = new ArrayList<String[]>();
		try {
			//execl文件解析，获取需要导入的数据
			dataList = ExcelUtil.analyzeExcelFile(files[0].getOriginalFilename(),
				files[0].getInputStream());
			if(dataList.isEmpty()){
				map.put("info", "信息不能为空");
				return map;
			}
		} catch (IOException e) {
			// TODO: handle exception
			map.put(Constants.ERROR_MSG, e.getMessage());
			return map;
		}
		List<PhoneMessageUsers> list = new ArrayList<>();
		List<String> l = new ArrayList<>();
		
		for (int i = 0; i < dataList.size(); i++) {
			String[] strArr = dataList.get(i);
			String userName = strArr[0].trim();//短信接收人
			String gender = strArr[1].trim();//性别
			Long mobilephone=0L;
			try{
				mobilephone = Long.parseLong(strArr[2].trim());//手机号码
			}catch(Exception e){
				l.add(userName+"的手机号格式不正确");
				continue;
			}
			//检查订单手机号码不能为空
			if(mobilephone<13000000000L){
				l.add(userName+"的手机号格式不正确");
				continue;
			}
			PhoneMessageUsers user=new PhoneMessageUsers();
			user.setUserName(userName);
			user.setGender(gender);
			user.setMobilephone(mobilephone);
			list.add(user);
		}
		int flag = 0;
		flag=phoneMessageUsersMapper.insert(list);
		//flage

		map.put("info", "成插入"+flag+"条");
		return map;
	}
	/**
	 * 查询所有电话
	 * @since 2016年12月7日
	 * @return
	 */
	public List<PhoneMessageUsers> getMobilephone_List(){
		return phoneMessageUsersMapper.getMobilephone_List();
	}
	
	/**
	 * 插入短信记录	
	 * @since 2016年12月7日
	 * @return
	 */
	public void insertMessageLogs(PhoneMessageLogs phoneMessageLogs){
		phoneMessageLogsMapper.insertMessageLogs(phoneMessageLogs);
	}
	
}
