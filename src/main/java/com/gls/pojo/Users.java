package com.gls.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
 * @author Ding
 * <br><pre>
 * drop table if exists users;
	create table users (
		user_id bigint auto_increment comment '自增id,用户id',
		level tinyint not null default 0 comment '用户等级,0普通;1中等;2高级, 与角色表的role区分开来',
		user_name varchar(20) not null default '' comment '用户名称',
		screen_name varchar(20) not null default '' comment '昵称',
		province varchar(10) not null default '' comment '省',
		city varchar(10) not null default '' comment '市',
		district varchar(5) not null default '' comment '区',
		birth_year int not null default 0 comment '出生年',
		birth_month tinyint not null default 0 comment '出生月',
		birth_day tinyint not null default 0 comment '出生日',
		addresss varchar(40) not null default 0 comment '收贷地址', 
		mobilephone bigint not null default 0 comment '手机号码, 这个在前端业务已经限制了其唯一性,所以不需要使用unique key',
		telephone bigint not null default 0 comment '固话',
		gender varchar(2) not null default '' comment '男,女',
		profile_image_url varchar(40) not null default '' comment 'profile图片url',
		online_status tinyint not null default 0 comment '0:不在线,1:在线',
		online_time int not null default 0 comment '登陆总时长',
		description varchar(30) not null default '' comment '备注',
		email varchar(30) not null default '' comment '邮件',
		passwd varchar(32) not null default '' comment '密码',
		company varchar(25) not null default '' comment '公司',
		token_content varchar(32) not null default '' comment '可以是ip地址,用户设备的序列号等,辅助登陆用的',
		device_id tinyint not null default 0 comment '用户的设备信息, 如11:iphone6; 12:iphone5; 21:Android等',
	
		platform_id tinyint not null default 0 comment '平台id, 如0默认的就是手机注册的; 1: 微信; 2: QQ; 3:微博',
		token varchar(32) not null default '' comment '预留字段, 现在是放在redis里',
		open_id varchar(40) not null default '' comment '第三方账号id',
		
		create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间',
		update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
		is_deleted tinyint not null default 0 comment '删除标识,0.可用，1.已删除不可用',
		primary key(user_id)
	) comment '会员表';
	alter table users add key k_mp_pw(mobilephone, passwd);
	alter table users add key k_opid(open_id);
	
	此贷必须 extends BasePojo
 * </pre>
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Users extends BasePojo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//add Ding 20150625 For LoginIn Start (用以登陆用的)(现在还不是好确定是不是一定要用,预留吧)
	private String validateCode;	//手机验证码
	private String token;	//token 的key
	private String tokenContent;	//可以是ip地址,用户设备的序列号等
	private int deviceId;	//用户的设备信息, 如11:iphone6; 12:iphone5; 21:Android等
	//add Ding 20150625 For LoginIn End 
	
	//社交账号 Start
	private int platformId;
	private String openId;
	private String wxOpenId;//微信OPEN_ID
	private String mpOpenId;//公众号OPEN_ID
	private int pid;
	//社交账号 End
	//用户收藏的标签列表
	private int[] tagIdList;
	
	private long userId;
	private long custId;
	private String custName;
	private long custPid;
	private long addressId;
	private int level;
	private int levelStatus;
	private String levelName;
	private String userName;//用户名称
	private String screenName;//昵称/真实姓名
	private String province;
	private String city;
	private String district;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	private String addresss;
	private long mobilephone;//手机号码
	private long telephone;
	private String gender;
	private String profileImageUrl;
	private int onlineStatus;
	private int onlineTime;
	
	private int userType;
	private int subscribe;//是否关注公众号
	
	private int count;
	
	private int isProxy;//是否代理，0：不是，1是
	//新增 ---zyl--2017-04-19
	private String position;//职务
	private int apply_status;//申请状态，0：申请中，1：通过审批，2：不通过
	
	
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getApply_status() {
		return apply_status;
	}

	public void setApply_status(int apply_status) {
		this.apply_status = apply_status;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public long getCustPid() {
		return custPid;
	}

	public void setCustPid(long custPid) {
		this.custPid = custPid;
	}

	private double allPrice;
	
	
	
	public String getWxOpenId() {
		return wxOpenId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getMpOpenId() {
		return mpOpenId;
	}

	public void setMpOpenId(String mpOpenId) {
		this.mpOpenId = mpOpenId;
	}

	private String description;
	private String email;
	private int sortType;//排序类型。0默认排序，1：销量，2最新，3价格
	private int sortValue;//排序值，主要是价格 0 高到低，1 低到高
	
	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	private String passwd;
	public int getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(int levelStatus) {
		this.levelStatus = levelStatus;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private String company;//公司名称
	private Timestamp createTime;
	private Timestamp updateTime;
	
	public double getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}

	//拿 到闪购列表 时，用flashEndTime与当前时间对比时用到的，如果比当前时间 早就 筛选掉
	private Timestamp flashNowTime;
	
	private int isDeleted;
	public Users(){}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this. userId = userId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this. level = level;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this. userName = userName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this. screenName = screenName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this. province = province;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this. city = city;
	}

	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this. district = district;
	}
	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this. birthYear = birthYear;
	}
	public int getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(int birthMonth) {
		this. birthMonth = birthMonth;
	}
	public int getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(int birthDay) {
		this. birthDay = birthDay;
	}
	public int[] getTagIdList() {
		return tagIdList;
	}

	public void setTagIdList(int[] tagIdList) {
		this.tagIdList = tagIdList;
	}

	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public int getSortValue() {
		return sortValue;
	}

	public void setSortValue(int sortValue) {
		this.sortValue = sortValue;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAddresss() {
		return addresss;
	}
	public void setAddresss(String addresss) {
		this. addresss = addresss;
	}

	public long getMobilephone() {
		return mobilephone;
	}
	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public void setMobilephone(long mobilephone) {
		this. mobilephone = mobilephone;
	}
	public long getTelephone() {
		return telephone;
	}
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getTokenContent() {
		return tokenContent;
	}

	public void setTokenContent(String tokenContent) {
		this.tokenContent = tokenContent;
	}

	public void setTelephone(long telephone) {
		this. telephone = telephone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this. gender = gender;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this. profileImageUrl = profileImageUrl;
	}
	public int getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(int onlineStatus) {
		this. onlineStatus = onlineStatus;
	}
	public int getOnlineTime() {
		return onlineTime;
	}
	public int getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(int isProxy) {
		this.isProxy = isProxy;
	}

	public void setOnlineTime(int onlineTime) {
		this. onlineTime = onlineTime;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this. description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this. email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this. passwd = passwd;
	}
	public Timestamp getFlashNowTime() {
		return flashNowTime;
	}

	public void setFlashNowTime(Timestamp flashNowTime) {
		this.flashNowTime = flashNowTime;
	}

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this. company = company;
	}
	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public String getOpenId() {
		return openId;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this. createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this. updateTime = updateTime;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this. isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Users [tokenContent=" + tokenContent + ", deviceId=" + deviceId + ", userId=" + userId + ", addressId="
				+ addressId + ", userName=" + userName + ", screenName=" + screenName + ", mobilephone=" + mobilephone
				+ ", profileImageUrl=" + profileImageUrl + "]"
				+ ", pid=" + pid+ "]";
	}

	

}