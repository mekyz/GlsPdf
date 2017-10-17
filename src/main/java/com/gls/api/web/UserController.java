package com.gls.api.web;


import com.gls.api.service.PhoneipService;
import com.gls.api.service.SMSService;
import com.gls.api.service.UserService;
import com.gls.api.service.rabbitmq.RabbitQueueService;
import com.gls.exception.ExceptionConstroller;
import com.gls.pojo.*;
import com.gls.util.*;
import org.apache.http.entity.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户Controler
 * <br>
 手机登陆流程
 login() 普通登陆(必须先手机注册)


 * </pre>
 * @author Ding
 * @since 2015-06-24
 */
@Controller
public class UserController extends ExceptionConstroller {

	@Autowired
	private UserService userService;
	@Autowired
	private SMSService smsService;
	@Autowired
	private PhoneipService phoneipService;
	@Autowired
	private RabbitQueueService rabbitQueueService;
	@Autowired
	private NumberBuilder numberBuilder;


	@Resource(name = "redisTemplate")
	public HashOperations<String, String, String> hashOperations;
	@Resource(name = "redisTemplate")
	public RedisTemplate<String, String> redisTemplate;
	@Resource(name = "redisTemplate")
	public ValueOperations<String, String> valueOperations;
	@Value("${payurl}")
	private String payHost;
	@Value("${openId}")
	private String openId;
	@Value("${openKey}")
	private String openKey;




	/**
	 * <b style="color:blue">登陆页面/ 登陆成功后返回token给客户端  (重新登陆的场景)</b>
	 * <br>调用demo：http://120.132.84.162/login
	 * @author Ding
	 * @since 2015-06-25
	 * @param user 登陆的用户obj(比如可以传用户手机号码,用户的社交账号的platformId,uid等)
	 * 			<br>必传User对象的属性字段:
	 * 			<br>mobilephone		手机
	 * 			<br>passwd			密码/从前端传过来的最好是md5()后的32位string, 你懂的
	 * 			<br>tokenContent 	可以是用户设备的序列号, 可以是ip地址
	 * <pre>
	{
	"mobilephone":15920159266,
	"token":"qwertqwert",
	"passwd":"123",
	"tokenContent":"ddfdfdf"
	}
	 * </pre>
	 *
	 * @return status 0:登陆成功; -66:登陆不成功; -10:密码错误;
	 * <br> token
	 * <br> userId
	 * <br> user   对象
	 * 			<br>返回Exception模板：-22:Server Exception; -33:DB出错; -44:redis出错; -55:IO出错
	 * <br> errorInfo  详细的错误信息描述
	 * <br> productCounts  购物车选中商品个数, 若没有则为0
	 * @see Users
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map login(@RequestBody Users user, HttpServletRequest request) throws JSONException {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<>();
		JSONObject json = new JSONObject();
		json.accumulate("usersAcount",user.getUserName());
		json.accumulate("usersPwd",user.getPasswd());
		String data = HttpUtil.doPost("http://test.g2l-service.com/bigdata/bigDataLogin", json.toString(), ContentType.APPLICATION_JSON.getMimeType());
		JSONObject result = new JSONObject(data);
		if (result.getInt("code") == -2){
			if (result.getString("msg") == "账户使用时间已到期") {
				map.put(Constants.ERROR_CODE, Constants.FAIL_LOGIN_CODE);
				map.put(Constants.ERROR_MSG, result.getString("msg"));
				return map;
			}
			int userCount = 0;
			user.setPasswd(CodeUtil.string2MD5(user.getPasswd()));		//这一步最好放在前端做
			userCount = userService.varidateUserPsword(user);		//这里验证mobilephone/ password
			if (userCount > 0) {
				//查询user对象出来,主要是拿userId
				List<Users> userList = userService.seleUser4Phone(user);
				if (userList.size() > 0) {
					if( userList.get(0).getApply_status()==0){
						map.put(Constants.ERROR_CODE, Constants.STATUS_LOGIN_CODE);
						map.put(Constants.ERROR_MSG, Constants.STATUS_LOGIN_MSG);
					}else if( userList.get(0).getApply_status()==2){
						map.put(Constants.ERROR_CODE, Constants.STATUS1_LOGIN_CODE);
						map.put(Constants.ERROR_MSG, Constants.STATUS1_LOGIN_MSG);
					}else{
						String token = CodeUtil.generateToken(user.getTokenContent());
						user.setToken(token);
						user.setUserId(userList.get(0).getUserId());
						map.put(Constants.DATA, token);
						map.put(Constants.UID, user.getUserId());
						map.put("user", userList.get(0));
						//保存token到redis
						int saveTime = 60*60;	//==25920000秒 300天 60*60*24
						userService.setValueAndExpire(token, String.valueOf(user.getUserId()), saveTime);
						map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
						if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
							Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " loginin ok   " + user);
						}
					}
				} else {
					if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
						Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " loginin fail   " + user);
					}
					map.put(Constants.ERROR_CODE, Constants.FAIL_LOGIN_CODE);
					map.put(Constants.ERROR_MSG, Constants.FAIL_LOGIN_MSG);
				}
			}

		}else if (result.getInt("code") ==1){
			user.setPasswd(CodeUtil.string2MD5(user.getPasswd()));
			user.setScreenName(user.getUserName());
			String token = CodeUtil.generateToken(CodeUtil.clientIp(request));
			user.setToken(token);
			JSONObject gls_user = new JSONObject(result.getString("obj"));
			user.setTelephone(Long.parseLong(gls_user.getString("phone")));
			user.setMobilephone(Long.parseLong(gls_user.getString("usersPhone")));
			user.setEmail("未填");
			user.setCompany("gls未传");
			user.setPosition("gls未传");
			user.setDeviceId(0);
			user.setPlatformId(0);
			user.setCreateTime(new Timestamp(System.currentTimeMillis()));
			user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			user.setApply_status(1);
			user.setIsDeleted(0);
			List<Users> userList = userService.seleUser4Phone(user);
			if (userList.size() <= 0) {
				userService.insertUser(user);
				UserAccount  userAccount=new UserAccount();
				userAccount.setPay_points(0);//充值积分
				userAccount.setAll_points(0);//总积分
				userAccount.setFree_points(0);//赠送积分;
				userService.insertAccount(userAccount);
				userList = userService.seleUser4Phone(user);
			}
			map.put(Constants.DATA, token);
			map.put(Constants.UID, userList.get(0).getUserId());
			map.put("user", userList.get(0));
			//保存token到redis
			int saveTime = 60*60;	//==25920000秒 300天 60*60*24
			userService.setValueAndExpire(token, String.valueOf(userList.get(0).getUserId()), saveTime);
			map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
		}else {
			map.put(Constants.ERROR_CODE, Constants.FAIL_LOGIN_CODE);
			map.put(Constants.ERROR_MSG, result.getString("msg"));
		}

		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;

	}

	/**
	 * <b style="color:blue">退出登陆页面</b>
	 * <br>调用demo：http://120.132.84.162/logout
	 * @author Ding
	 * @since 2015-06-25
	 * @param user 登陆的用户obj(传用户的token)
	 * @return status 0:getout成功;
	 * 			<br>返回Exception模板：-22:Server Exception; -33:DB出错; -44:redis出错; -55:IO出错
	 * @see Users
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = Constants.JSON)
	@ResponseBody
	public Map logout(@RequestBody Users user, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//判断token权限
		if (!userService.hasKey(user.getToken(), user.getUserId())) {
			map.put(Constants.ERROR_CODE, Constants.NO_LOGIN_CODE);
			map.put(Constants.ERROR_MSG, Constants.NO_LOGIN_MSG);
			return map;
		}

		//把token从redis干掉
		userService.logout(user);
		map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}
	/**
	 * <b style="color:blue">test</b>
	 * @throws JSONException
	 * @see Users
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Map test(HttpServletRequest request) throws JSONException {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			//Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Map<String, Object> map = new HashMap<String, Object>();

		phoneipService.sendToPhone("15889734575", "测试一下");
		map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}
	/**
	 * <b style="color:blue">注册页面/ 登陆成功后返回token给客户端  (重新登陆的场景)</b>
	 * <br>调用demo：http://120.132.84.162/register
	 * @author
	 * @since
	 * @param user 登陆的用户obj(比如可以传用户手机号码,用户的社交账号的platformId,uid等)
	 * 			<br>必传User对象的属性字段:
	 * 			<br>mobilephone		手机
	 * 			<br>passwd			密码/从前端传过来的最好是md5()后的32位string, 你懂的
	 * 			<br>tokenContent 	可以是用户设备的序列号, 可以是ip地址
	 * <pre>
	{
	"userName":"admin",
	"screenName":"qwertqwert",
	"mobilephone":"123",
	"email":"ddfdfdf",
	"passwd":15920159266,
	"company":"qwertqwert",
	"position":"123"

	}
	 * </pre>
	 *
	 * @return status 0:登陆成功; -66:登陆不成功; -10:密码错误;
	 * <br> token
	 * <br> userId
	 * <br> user   对象
	 * 			<br>返回Exception模板：-22:Server Exception; -33:DB出错; -44:redis出错; -55:IO出错
	 * <br> errorInfo  详细的错误信息描述
	 * <br> productCounts  购物车选中商品个数, 若没有则为0
	 * @see Users
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Map register(@RequestBody Users user, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		int userCount = 0;
		userCount = userService.checkUserName(user);		//

		if (userCount<= 0&&user.getPosition()!=null) {
			user.setPasswd(CodeUtil.string2MD5(user.getPasswd()));		//这一步最好放在前端做
			//查询user对象出来,主要是拿userId
			try {
				userService.insertUser(user);
				UserAccount  userAccount=new UserAccount();
				userAccount.setPay_points(0);//充值积分
				userAccount.setAll_points(1000);//总积分
				userAccount.setFree_points(1000);//赠送积分;
				userService.insertAccount(userAccount);
				UserBill userBill=new UserBill();
				userBill.setUserId(user.getUserId());
				userBill.setTitle("System Gift");//
				userBill.setType(0);
				userBill.setDetailId(0);
				userBill.setPoints(1000);
				userService.insertUserBill(userBill);//插入交易记录
				map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);//成功
			} catch (Exception e) {
				// TODO: handle exception
				map.put(Constants.ERROR_CODE, Constants.ERROR_VALIDATE_CODE);
				map.put(Constants.ERROR_MSG, Constants.ERROR_VALIDATE_MSG);
			}


		} else if(userCount<= 0) {
			map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);

		}else {
			map.put(Constants.ERROR_CODE, Constants.ERROR_REG_CODE);
			map.put(Constants.ERROR_MSG, Constants.ERROR_REG_MSG);
		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}

	/**
	 * admin后台审核列表
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public Map getUserList(@RequestBody Users user, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		if(user.getCurrentPage()!=null && user.getNumber() !=null){
			user.setNumber(10);
			user.setCurrentPage((user.getCurrentPage()-1)*user.getNumber());

		}else{ //默认
			user.setCurrentPage(0);
			user.setNumber(10);
		}
		List<Users> userList = userService.getUserList(user);		//
		int count=userService.getUserCount(user);

		int countPage = (count  +  user.getNumber()  - 1) / user.getNumber();
		if (userList.size()>0) {
			map.put("userList", userList);
			map.put("count",count);//总条数
			map.put("countPage",countPage);//总页数
			map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
		} else {
			map.put(Constants.ERROR_CODE, Constants.ERROR_REG_CODE);
		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}
	/**
	 * admin后台审核列表 -->通过ID查看详情
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map getUserById(@RequestBody Users user, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		if (user.getUserId()>0) {
			Users users = userService.getUserById(user);
			map.put("user", users);
			map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
		} else {
			map.put(Constants.ERROR_CODE, Constants.ERROR_REG_CODE);

		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}
	/**
	 * admin后台审核列表 -->通过ID查看详情--->修改权限
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setUserStart", method = RequestMethod.POST)
	@ResponseBody
	public Map setUserStart(@RequestBody Users user, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + user);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		Users u=userService.getUserById(user);
		if (u!=null) {
			userService.setUserStart(user);
			map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
		} else {
			map.put(Constants.ERROR_CODE, Constants.ERROR_REG_CODE);

		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}
	/**
	 * 查看  用户账户 表 --（剩余积分）
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findAccountByUserId", method = RequestMethod.POST)
	@ResponseBody
	public Map findAccountByUserId(@RequestBody UserAccount userAccount, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + userAccount);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<>();
		try {
			//判断token权限
			if (!userService.hasKey(userAccount.getToken(), userAccount.getUserId())) {
				map.put(Constants.ERROR_CODE, Constants.NO_LOGIN_CODE);
				map.put(Constants.ERROR_MSG, Constants.NO_LOGIN_MSG);
				return map;
			}

			int count=userService.checkAccount(userAccount);
			if (count>0) {
				userAccount= userService.findAccountByUserId(userAccount);

				//map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
			} else {
				userAccount.setPay_points(0);//充值积分
				userAccount.setAll_points(1000);//总积分
				userAccount.setFree_points(1000);//赠送积分
				userService.insertAccount(userAccount);
				//map.put(Constants.ERROR_CODE, Constants.ERROR_REG_CODE);

			}
			map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
			map.put("account",userAccount);
		} catch (Exception e) {
			// TODO: handle exception
			map.put(Constants.ERROR_CODE, Constants.DB_ERROR_CODE);
		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}
	/**
	 * 查看  用户账户 表 -->充值历史纪录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserBillList", method = RequestMethod.POST)
	@ResponseBody
	public Map getUserBillList(@RequestBody  UserBill userBill, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + userBill);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		//判断token权限
		if (!userService.hasKey(userBill.getToken(), userBill.getUserId())) {
			map.put(Constants.ERROR_CODE, Constants.NO_LOGIN_CODE);
			map.put(Constants.ERROR_MSG, Constants.NO_LOGIN_MSG);
			return map;
		}
		try {
			if(userBill.getCurrentPage()!=null && userBill.getNumber() !=null){
				userBill.setNumber(5);
				userBill.setCurrentPage((userBill.getCurrentPage()-1)*userBill.getNumber());

			}else{ //默认
				userBill.setCurrentPage(0);
				userBill.setNumber(5);
			}

			List<UserBill> userBills=userService.getUserBillList(userBill);
			int count=userService.getUserBillListCount(userBill);
			int countPage = (count  +  userBill.getNumber()  - 1) / userBill.getNumber();
			if (userBills!=null) {
				map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
				map.put("userBills",userBills);
				map.put("count",count);//总条数
				map.put("countPage",countPage);//总页数
			} else {

				map.put(Constants.ERROR_CODE, Constants.ERROR_REG_CODE);

			}

		} catch (Exception e) {
			// TODO: handle exception
			map.put(Constants.ERROR_CODE, Constants.DB_ERROR_CODE);
		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}
	/**
	 * 查看 用户查询的历史记录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSearchLog", method = RequestMethod.POST)
	@ResponseBody
	public Map getUserLogList(@RequestBody  UserSearchLog userSearchLog, HttpServletRequest request) {
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " <  " + userSearchLog);
		}
		Loggers.WEB_ERROR_LOGGER.info("X-Real-IP >> " + (CodeUtil.clientIp(request)) + request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		//判断token权限
		if (!userService.hasKey(userSearchLog.getToken(), userSearchLog.getUserId())) {
			map.put(Constants.ERROR_CODE, Constants.NO_LOGIN_CODE);
			map.put(Constants.ERROR_MSG, Constants.NO_LOGIN_MSG);
			return map;
		}
		try{
			List<UserSearchLog> userSearchLogs=userService.getSearchLogListByUserId(userSearchLog);
			if (userSearchLogs!=null) {
				map.put("Logs",userSearchLogs);
				map.put(Constants.ERROR_CODE, Constants.OK_ERROR_CODE);
			} else {
				map.put(Constants.ERROR_CODE, -1);
				map.put(Constants.ERROR_MSG, "没有记录");
			}

		} catch (Exception e) {
			// TODO: handle exception
			map.put(Constants.ERROR_CODE, Constants.DB_ERROR_CODE);
		}
		if (Loggers.WEB_ERROR_LOGGER.isDebugEnabled()) {
			Loggers.WEB_ERROR_LOGGER.debug(request.getRequestURI() + " End >>> ");
		}
		return map;
	}

}
