package com.gls.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gls.dao.mapper.TestMapper;

@Service
public class TestService {
	
	@Autowired
	private TestMapper testMapper;
	
	public void handleData(){
		List<Map<String, Object>> district4 = testMapper.getDistrict4();
		
		for (Map<String,Object> district: district4) {
			Long did = (Long) district.get("did");
			
			List<Map<String, Object>> productList = testMapper.getProductListByDid(did);
			
			
		}
	}

}
