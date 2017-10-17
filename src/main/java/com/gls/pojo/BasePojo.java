package com.gls.pojo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gls.util.Constants;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BasePojo {

	private String token;
	private String tokenContent;
	private long userId;
	private int currentPage = 1;
	private int number = 20;

	private Integer totalCount;
	private Integer totalPage;

	private Integer startIndex;	//从哪条记录开始
	private Integer endIndex;	//显示到哪条记录结束

	private int offset;

	private String orderby = "";
	private Integer desc;		//这个不用的了
	private String cDate;
	private String uDate;
	private int type;//查看的收费项目类型，0：联系人，1：报告
	private String title;//标题
	
	
	
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	public String getuDate() {
		return uDate;
	}

	public void setuDate(String uDate) {
		this.uDate = uDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public BasePojo() {
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
		this.offset = (this.currentPage - 1) * this.number;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
		this.offset = (this.currentPage - 1) * this.number;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		if (totalCount != null) {
			return this.totalCount % this.number == 0 ? this.totalCount
					/ this.number : this.totalCount / this.number + 1;
		} else {
			return null;
		}
	}

//	public void setTotalPage(int totalPage) {
//		this.totalPage = totalPage;
//	}

	public int getOffset() {
		return offset;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getTokenContent() {
		return tokenContent;
	}

	public void setTokenContent(String tokenContent) {
		this.tokenContent = tokenContent;
	}

	public Integer getDesc() {
		return desc;
	}


	public void setDesc(Integer desc) { 
		this.desc = desc;
	}

	public Integer getStartIndex() {
		if (totalPage != null) {
			return (this.currentPage - 1) * this.number == 0 ? 1
					: (this.currentPage - 1) * this.number + 1;
		} else {
			return null;
		}
	}

	public Integer getEndIndex() {
		if (totalPage != null) {
			return this.currentPage == getTotalPage() ? this.totalCount
					: this.currentPage * this.number;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "BasePojo [token=" + token + ", userId=" + userId
				+ ", currentPage=" + currentPage + ", number=" + number
				+ ", totalCount=" + totalCount + ", totalPage=" + totalPage
				+ ", startIndex=" + startIndex + ", endIndex=" + endIndex
				+ ", offset=" + offset + ", orderby=" + orderby + ", desc="
				+ desc + "]";
	}

}
