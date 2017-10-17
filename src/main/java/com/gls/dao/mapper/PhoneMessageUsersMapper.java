package com.gls.dao.mapper;

import java.util.List;

import com.gls.pojo.PhoneMessageUsers;

public interface PhoneMessageUsersMapper {

    int insert(List<PhoneMessageUsers> record);

	/**
	 * 查询所有电话
	 * @since 2016年12月7日
	 * @return
	 */
	List<PhoneMessageUsers> getMobilephone_List();

}