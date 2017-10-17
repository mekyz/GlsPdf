package com.gls.pojo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

//作为前端传过来的一个方便的对象1
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RequestObj extends BasePojo {
	
	private int type;//参数类型，0，采购商，1供应商
	
	private String name;//名称

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
