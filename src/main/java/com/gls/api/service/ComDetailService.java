package com.gls.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gls.dao.mapper.ComDetailMapper;
import com.gls.pojo.RequestObj;

/**
 * 公司详情相关Controller
 * @author Ding
 * @since 2017年3月6日
 */
@Service
public class ComDetailService {
	
	@Autowired
    private ComDetailMapper comDetailMapper;
	
	/**
	 * 根据公司名字查找匹配的公司，公司名字唯一
	 * @param name
	 * @param type
	 * @return
	 */
	public Integer getComIdByName(RequestObj requestObj){
		return comDetailMapper.getComIdByName(requestObj);
	}

}
