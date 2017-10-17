package com.gls.api.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gls.search.entity.FrankOrderEntity;
import com.gls.util.Constants;

/**
 *  初始化数据
 * @author Administrator
 *
 */
@Controller
public class InitController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/reload",produces = Constants.JSON)
    @ResponseBody
    public String reload(FrankOrderEntity order,String by_startDate,String by_endDate,Integer pageNo,Integer pageSize){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("country_name", order.getCountryName());
        map.put("pro_desc", order.getProDesc());
        String result = JSON.toJSONString(map);
        
        return result;
        
    }
}
