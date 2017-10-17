package com.gls.pojo;

public class ContactsBeen {

	private String acquired; //w未知字段，默认为null
	private String city; //公司所在城市，对应地区表第三级
	private String company; //公司名
	private String companyGuid; // 公司guid
	private String companyId; //公司平台ID
	private String country; //公司所在国家
	private String directDial; //未知字段，默认为false
	private String guid; //员工guid
	private String id; //员工ID 
	private String inactive; //未知字段，默认为false
	private String name; // 员工姓名（ lastname  firstname）
	private String owned;  //boolean 类型，未知字段
	private String royaltyAcquired;  //未知字段，默认为Null
	private String state;  //未知字段
	private String title;	// 职位描述
	private String updated;  //修改日期
	public String getAcquired() {
		return acquired;
	}
	public void setAcquired(String acquired) {
		this.acquired = acquired;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyGuid() {
		return companyGuid;
	}
	public void setCompanyGuid(String companyGuid) {
		this.companyGuid = companyGuid;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDirectDial() {
		return directDial;
	}
	public void setDirectDial(String directDial) {
		this.directDial = directDial;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInactive() {
		return inactive;
	}
	public void setInactive(String inactive) {
		this.inactive = inactive;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwned() {
		return owned;
	}
	public void setOwned(String owned) {
		this.owned = owned;
	}
	public String getRoyaltyAcquired() {
		return royaltyAcquired;
	}
	public void setRoyaltyAcquired(String royaltyAcquired) {
		this.royaltyAcquired = royaltyAcquired;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
}
