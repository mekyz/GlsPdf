package com.gls.dao.mapper;

import java.util.List;
import java.util.Map;

import com.gls.pojo.Users;

public interface TestMapper {
	
	public List<Map<String, Object>> getDistrict4();
	
	public List<Map<String, Object>> getProductListByDid(Long did);
	
	

}