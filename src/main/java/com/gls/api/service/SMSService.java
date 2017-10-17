package com.gls.api.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.gls.api.service.redis.RedisService;
import com.gls.util.CodeUtil;
import com.gls.util.Constants;
import com.gls.util.Loggers;

@Service
public class SMSService extends RedisService {

	public String generateSMSToken(String ipAddress) {
		String token = "";
		if (null == ipAddress) {
			ipAddress = "";
		}
		String fullKey = Constants.REDIS_SMS + ipAddress;
		// System.out.println("ipAddress
		// ------------------------------------------------------------ >" +
		// fullKey);
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug("ipAddress --------------------------------------------- >" + fullKey);
		}
		// 生成新的key
		token = CodeUtil.generateToken(fullKey);
		long saveTime = 60 * 10; // 10分钟
		valueOperations.set(token, ipAddress);
		redisTemplate.expire(token, saveTime, TimeUnit.SECONDS);
		
		//=======================================================存到redis里记录数, 后端定时器判断个数是否大于一个数，大于则发信息到bill  ===Start 
		//加1 存到redis里
		if (null != ipAddress) {
			long redisFlag = hashOperations.increment(Constants.REDIS_KEY_SEND_IP_HASH, ipAddress, 1);
			
			/*
			 * if (hasKey(fullKey)) { // 如果有此IP记录 那么返回同样的token 并且不减少hash expire时间
			 * 短信当天最高发送条数减少一个 Integer leftSMSCount =
			 * Integer.valueOf(hashOperations.get(fullKey,Constants.
			 * REDIS_SMS_MAXDAY_COUNT_KEYNAME)); if (leftSMSCount > 0) {
			 * leftSMSCount--;
			 * hashOperations.put(fullKey,Constants.REDIS_SMS_MAXDAY_COUNT_KEYNAME,
			 * leftSMSCount.toString()); token = hashOperations.get(fullKey,
			 * Constants.TOKEN); }else { hashOperations.delete(fullKey,
			 * Constants.TOKEN); }
			 * 
			 * } else { //生成新的key token = CodeUtil.generateToken(fullKey);
			 * hashOperations.put(fullKey,Constants.REDIS_SMS_MAXDAY_COUNT_KEYNAME,
			 * Constants.REDIS_SMS_MAXDAY_COUNT_VALUE); hashOperations.put(fullKey,
			 * Constants.TOKEN, token); //单个IP过期时间为100天 setHashExpire(fullKey,
			 * 60*60*24*100); }
			 */
			
			
			if (redisFlag <= 0) {
				Loggers.WEB_ERROR_LOGGER.info("generateSMSToken hashOperations.increment fail ip >>> " + ipAddress);
			}
		}
		//=======================================================存到redis里记录数, 后端定时器判断个数是否大于一个数，大于则发信息到bill  ===End 
		
		// hashOperations.put(fullKey,Constants.REDIS_SMS_MAXDAY_COUNT_KEYNAME,Constants.REDIS_SMS_MAXDAY_COUNT_VALUE);
		// hashOperations.put(fullKey, Constants.TOKEN, token);
		// 单个IP过期时间为100天

		// 保存token到redis
//		long saveTime = 60 * 60 * 24 * 300; // ==25920000秒 300天
//		userService.setValueAndExpire(token, String.valueOf(user.getMobilephone()), saveTime);

		// setHashExpire(fullKey, saveTime);

		/*
		 * if (hasKey(fullKey)) { // 如果有此IP记录 那么返回同样的token 并且不减少hash expire时间
		 * 短信当天最高发送条数减少一个 Integer leftSMSCount =
		 * Integer.valueOf(hashOperations.get(fullKey,Constants.
		 * REDIS_SMS_MAXDAY_COUNT_KEYNAME)); if (leftSMSCount > 0) {
		 * leftSMSCount--;
		 * hashOperations.put(fullKey,Constants.REDIS_SMS_MAXDAY_COUNT_KEYNAME,
		 * leftSMSCount.toString()); token = hashOperations.get(fullKey,
		 * Constants.TOKEN); }else { hashOperations.delete(fullKey,
		 * Constants.TOKEN); }
		 * 
		 * } else { //生成新的key token = CodeUtil.generateToken(fullKey);
		 * hashOperations.put(fullKey,Constants.REDIS_SMS_MAXDAY_COUNT_KEYNAME,
		 * Constants.REDIS_SMS_MAXDAY_COUNT_VALUE); hashOperations.put(fullKey,
		 * Constants.TOKEN, token); //单个IP过期时间为100天 setHashExpire(fullKey,
		 * 60*60*24*100); }
		 */

		return token;

	}

	//验证完并不删除cache
	public Boolean validToken(String ip, String token) {
		
		Boolean validBoolean = false;
		
		//这里判断 此ip 今天发送信息有没有超过 xxx条, 如果有则禁止
		if (hashOperations.hasKey(Constants.REDIS_KEY_SEND_IP_HASH, ip)) {
			int ipSendCount = Integer.valueOf(hashOperations.get(Constants.REDIS_KEY_SEND_IP_HASH, ip));
			//phoneip里的ip 每个ip一天允许发多少条短信，  500条短信大概 40元， 一条短信大概8分
			if (ipSendCount > Constants.REDIS_KEY_SEND_IPCOUNT_HASH_SIZE) {
				return false;
			}
		}
		
//		if (redisTemplate.hasKey(token)) {
			if (ip.equals(valueOperations.get(token))) {
				validBoolean = true;
			}
//		}
//		if (hashOperations.hasKey(Constants.REDIS_SMS + ip, Constants.TOKEN)) {
//			if (token.equals(hashOperations.get(Constants.REDIS_SMS + ip, Constants.TOKEN)))
//				validBoolean = true;
//		}
		return validBoolean;
	}
	
	//现在只用于web的图形验证, 验证后 立刻删除了此cache
	public Boolean validTokenAndDel4Web(String ip, String code) {
		
		Boolean validBoolean = false;
		String key = code + ":" + ip;
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
			validBoolean = true;
		} 
		return validBoolean;
	}
}
