package com.gls.dao.mapper;

import com.gls.pojo.PhoneMessageLogs;
import com.gls.pojo.PhoneMessageUsers;

public interface PhoneMessageLogsMapper {
	
	/**
	 * 插入短信记录	
	 * @since 2016年12月7日
	 * @return
	 */
	public void insertMessageLogs(PhoneMessageLogs phoneMessageLogs);
}