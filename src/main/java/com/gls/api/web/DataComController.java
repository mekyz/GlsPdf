package com.gls.api.web;

import java.util.List;
import java.util.Map;

import com.gls.api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gls.api.service.DataComService;
import com.gls.api.service.HomeService;
import com.gls.api.service.UserService;
import com.gls.exception.ExceptionConstroller;
import com.gls.pojo.ProductParam;
import com.gls.search.util.ResponseResult;
import com.gls.util.Constants;

/**
 * Data.com公司联系人对接Controller
 * @author Administrator
 *
 */
@Controller
public class DataComController extends ExceptionConstroller{
	
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 @Autowired
	private DataComService dataComService;
	 @Autowired
	 private UserService userService;
	 @Autowired
     private ProductService productService;
	/**
     * 
     * 功能描述：根据公司名模糊匹配data.com公司列表<br/>
     * 创建人: Ding<br/>
     * 创建日期:2017年4月21日<br/>
     * @param map
     * @return 公司联系人列表
     */
    @RequestMapping(value = "/findDataComList", method=RequestMethod.POST ,produces = Constants.JSON)
    @ResponseBody
    public String findComListData(@RequestBody ProductParam productParam){
        String relust = null;
        
//        if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
//    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
//		}
        try{
            List<Map<String, Object>> listContacts = dataComService.findDataComList(productParam);
            relust = ResponseResult.createSuccess(listContacts).toJson();
        }catch(Exception e){
            logger.error("根据公司名模糊匹配data.com公司列表"+e);
        }
        return relust;
    }
    /**
     * 根据选择的公司获取公司联系人列表，保存
     * @param productParam
     * @return
     */
    @RequestMapping(value="/getComContact" , method=RequestMethod.POST , produces=Constants.JSON)
    @ResponseBody
    public String getComContact(@RequestBody ProductParam productParam) {
    	if (!userService.hasKey(productParam.getToken(), productParam.getUserId())) {
    		return ResponseResult.createFalied(Constants.NO_LOGIN_CODE).toJson();
		}
        String relust=null;
        try {
        	List<Map<String, Object>> listContacts = dataComService.getComContact(productParam);
            int count = productService.findContactsCount(productParam);
            relust = ResponseResult.createSuccess(listContacts,count).toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存联系人"+e);
        }
        return relust;
        
    }
}
