package com.gls.api.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.entity.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gls.api.service.UserService;
import com.gls.pojo.Orders;
import com.gls.pojo.PayReqDto;
import com.gls.pojo.UserAccount;
import com.gls.pojo.UserBill;
import com.gls.util.CodeUtil;
import com.gls.util.Constants;
import com.gls.util.HttpUtil;
import com.gls.util.Loggers;
import com.gls.util.NumberBuilder;

/**
 * 支付控制器
 * @author willian
 * @created 2017-07-07 10:39
 * @email 18702515157@163.com
 **/
@Controller  
public class PayController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private NumberBuilder numberBuilder;
	@Value("${payurl}")
    private  String payHost;
	@Value("${openId}")
	private String openId;
	@Value("${openKey}")
	private String openKey;
	
//	@RequestMapping(value = "/pay",produces = Constants.JSON,method = RequestMethod.POST)
//	@ResponseBody
	@RequestMapping(value = "/pay",produces = Constants.JSON,method = RequestMethod.POST)
    @ResponseBody
	public String pay(@RequestBody PayReqDto payReqDto,HttpServletRequest request) throws ParseException, JSONException, IOException {
	        JSONObject result = new JSONObject();
	        if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
				Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + "<<<" + payReqDto);
			}
	        //判断token权限
	        if (!userService.hasKey(payReqDto.getToken(), payReqDto.getUserId())) {
	            result.accumulate(Constants.ERROR_CODE,Constants.NO_LOGIN_CODE);
	            result.accumulate(Constants.ERROR_MSG,Constants.NO_LOGIN_MSG);
	            return result.toString();
	        }
	      //插入订单等待回调  通过orderSn判断是否支付状态
	        Double all_points = payReqDto.getAmount().doubleValue();
	        Orders orders = new Orders(payReqDto.getUserId(), payReqDto.getBody(), all_points.intValue() / 7, all_points, 0);
	        String orderSn = numberBuilder.toBuild(NumberBuilder.PC);
	        orders.setOrder_sn(orderSn); 
	        orders.setTitle("用户购买积分");
	        userService.insertOrders(orders);
	        payReqDto.setOrderSn(orderSn);
	        payReqDto.setOpenId(openId);
	        payReqDto.setOpenKey(openKey);
	        JSONObject json = new JSONObject(payReqDto);// 转成json
	        json.remove("class");
	        String data = HttpUtil.doPost(payHost, json.toString(), ContentType.APPLICATION_JSON.getMimeType());
	        if (data == null || "{}" == data) {
	            result.accumulate(Constants.ERROR_CODE, "-1");
	        } else {
	            result.accumulate(Constants.ERROR_CODE, "0");
	            result.accumulate("data", data);
	            result.accumulate("orderSn",orderSn);
	        }
	        if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
	            Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
	        }
	        return result.toString();
	}
	@RequestMapping(value = "/paySuccess",produces = Constants.JSON,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> paySuccess(@RequestBody PayReqDto payReqDto ,HttpServletRequest request ){
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + "<<<" + payReqDto);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + CodeUtil.clientIp(request) + request.getRequestURI());
		String orderSn = payReqDto.getOrderSn();
		Orders orders = new Orders();
		orders.setOrder_sn(orderSn);
		orders = userService.getOrder(orders);
		if (orders.getOrder_type() == 1) {
            map.put(Constants.ERROR_CODE, 0);
            map.put("data", "支付完成");
            //增加积分
            Integer points = orders.getPoints();
            Long userId = orders.getUserId();
            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(userId);
            userAccount.setPay_points(points);
            userService.setAccount(userAccount);
            //插入交易记录
            UserBill userBill = new UserBill();
            userBill.setUserId(userId);
            userBill.setTitle("Internal Recharge"); 
            userBill.setType(0);
            userBill.setDetailId(0); 
            userBill.setPoints(points); 
            userService.insertUserBill(userBill);
        }else{
            map.put(Constants.ERROR_CODE, -1);
            map.put("data", "支付未完成");
        }
		return map;
	}
}
