package com.gls.exception;

//统一是server exception
public class ServerException extends Exception {
	
	public ServerException() {
		super();
	}
	private String info;
	public ServerException(String info) {
		this.info = info;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
