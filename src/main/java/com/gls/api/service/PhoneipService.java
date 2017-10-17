package com.gls.api.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gls.api.service.redis.RedisService;
import com.gls.util.Constants;
import com.gls.util.Loggers;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Service
public class PhoneipService extends RedisService {
	@Value("${luosimaokey}")
    private String luosimaokey;	//短信key
	
	//发送验证码到用户手机 短消息
	public Map<String, Object> sendToPhone(String phone, String info) throws JSONException {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug("Service sendToPhone Start <<< ");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == phone || phone.length() < 11) {
			//手机号不对
			map.put(Constants.ERROR_CODE, Constants.DB_ERROR_CODE);
			return map;
		}
		
		//以下这段是  两种情况下都需要的，
		//生成短信验证码算法
//        String variaCode = CodeUtil.code4phone(4, 10);
		Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(Constants.LUOSIMAO_SEND_KEY_API, luosimaokey));
        if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug("sendToPhone " + phone + luosimaokey);
		}
        WebResource webResource = client.resource(Constants.LUOSIMAO_SEND_JSON);
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add(Constants.LUOSIMAO_KEY_MOBILE, phone);
        formData.add(Constants.LUOSIMAO_KEY_MESSAGE, info + Constants.LUOSIMAO_KEY_COMPANY_NAME);
        ClientResponse response =  webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
//        int status = response.getStatus();
        JSONObject jsonObj = new JSONObject(textEntity);
        int error_code = jsonObj.getInt("error");
        String error_msg = jsonObj.getString("msg");
        if (error_code == Constants.LUOSIMAO_RESULT_OK) {	//成功
        	map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
        } else {
    			Loggers.WEB_ERROR_LOGGER.error("Service sendToPhone fail   , error_code: " + error_code + " error_msg: " + error_msg);
        	map.put(Constants.ERROR_CODE, error_code);
        	map.put(Constants.ERROR_MSG, error_msg);
        }
        
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug("Service sendToPhone End <<< ");
		}
		
        return map;
//        -40	错误的手机号	检查手机号是否正确
//        -41	号码在黑名单中	号码因频繁发送或其他原因暂停发送，请联系客服确认
//        -42	验证码类短信发送频率过快	前台增加60秒获取限制
    }
	
	/**
	 * 短信群发
	 * @since 2016年12月7日
	 * @param phoneList
	 * 		13761428268,18521513391
	 * @param info
	 * @return
	 * @throws JSONException
	 */
	public Map<String, Object> sendToPhoneList(String phoneList, String info) throws JSONException {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug("Service sendToPhone Start <<< ");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == phoneList || phoneList.length() < 11) {
			//手机号不对
			map.put(Constants.ERROR_CODE, Constants.DB_ERROR_CODE);
			return map;
		}
		
		//以下这段是  两种情况下都需要的，
		//生成短信验证码算法
//	        String variaCode = CodeUtil.code4phone(4, 10);
		Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(Constants.LUOSIMAO_SEND_KEY_API, luosimaokey));
        if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug("sendToPhone " + phoneList + luosimaokey);
		}
        WebResource webResource = client.resource(Constants.LUOSIMAO_SEND_BATCH_JSON);
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add(Constants.LUOSIMAO_KEY_MOBILE_LIST, phoneList);
        formData.add(Constants.LUOSIMAO_KEY_MESSAGE, info + Constants.LUOSIMAO_KEY_COMPANY_NAME);
        ClientResponse response =  webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
//	        int status = response.getStatus();
        JSONObject jsonObj = new JSONObject(textEntity);
        int error_code = jsonObj.getInt("error");
        String error_msg = jsonObj.getString("msg");
        if (error_code == Constants.LUOSIMAO_RESULT_OK) {	//成功
        	map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
        } else {
    			Loggers.WEB_ERROR_LOGGER.error("Service sendToPhone fail   , error_code: " + error_code + " error_msg: " + error_msg);
        	map.put(Constants.ERROR_CODE, error_code);
        	map.put(Constants.ERROR_MSG, error_msg);
        }
        
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug("Service sendToPhone End <<< ");
		}
		
        return map;
//        -10	验证信息失败	检查api key是否和各种中心内的一致，调用传入是否正确
//        -20	短信余额不足	进入个人中心购买充值
//        -30	短信内容为空	检查调用传入参数：message
//        -31	短信内容存在敏感词	修改短信内容，更换词语
//        -32	短信内容缺少签名信息	短信内容末尾增加签名信息eg.【铁壳测试】
//        -34	签名不可用	在后台 短信->签名管理下进行添加签名
//        -40	错误的手机号	检查手机号是否正确
//        -43	号码数量太多	单次提交控制在10万个号码以内
//        -50	请求发送IP不在白名单内	查看IP白名单的设置
//        -60	定时时间为过去	检查定时的时间，取消定时或重新设定定时时间
    }
	
	
	
	//1 代表 api error
	//2代表 api info
	//3代表 api 发给运营君
	public Map sendToPhone4APIAlert(String info, int ... type) throws JSONException {
		String phList[] = Constants.API_CODER_ERROR_SENDPHONE_LIST;;
		if (type.length > 0) {
			switch (type[0]) {
			case 1:
				phList = Constants.API_CODER_ERROR_SENDPHONE_LIST;
				break;
			case 2:
				phList = Constants.API_CODER_INFO_SENDPHONE_LIST;
				break;
			case 3:
				phList = Constants.API_YUNYING_INFO_SENDPHONE_LIST;
				break;
				
			default:
				phList = Constants.API_CODER_ERROR_SENDPHONE_LIST;
				break;
			}
		}
		Map map = new HashMap();
		String resuInfo = info;
		for (int i = 0; i < phList.length; i++) {
			map = sendToPhone(phList[i],  Constants.LUOSIMAO_APIERROR_START + resuInfo);
		}
		return map;
	}
	
	
	
}
