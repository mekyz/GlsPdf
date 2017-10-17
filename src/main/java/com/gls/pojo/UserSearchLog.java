package com.gls.pojo;

import java.io.Serializable;


import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author meky
 * 
 */
//作为前端传过来的一个方便的对象1
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserSearchLog  extends BasePojo  {
	private long id;//id
	private long userId;//用户id
	private String content;//搜索内容
	private int type;//搜索类型0:产品名，1：公司名',
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public UserSearchLog(){}
	public UserSearchLog(long userId, String content, int type) {
		super();
		this.userId = userId;
		this.content = content;
		this.type = type;
	}
	
	
	
}
