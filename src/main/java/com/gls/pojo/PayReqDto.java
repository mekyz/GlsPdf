package com.gls.pojo;

import java.math.BigDecimal;

public class PayReqDto{
	
	private BigDecimal amount;//支付的金额（单位：分）
	
	private String body;//内容，订单内容 即商品详情

	private String channel; //支付渠道（目前接口暂只支持 支付宝网页支付'alipay_pc_direct'）

	private String orderSn;  //商户订单号（唯一的，不能重复）

	private Integer orderSource = 7;//订单来源，1：F2B,2:B2F,3:B2C,4门店     标识是那个平台过来的支付订单

	private String subject="积分充值";//商品名称

	private String successUrl; //支付完成地址

	private String openKey; //openkey

	private String openId; //openid
	
	private String token;
	
	private Long userId;

	private String jumpUrl;
	
	/** 购物车剩余数量 */
	private Integer count;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getOpenKey() {
		return openKey;
	}

	public void setOpenKey(String openKey) {
		this.openKey = openKey;
	}

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}
