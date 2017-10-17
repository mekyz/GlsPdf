package com.gls.util;

/**
 * 分页工具
 * @author wb
 *
 */
public class Page {

	/**
	 * 当前页码
	 */
	private int pageNo;
	
	/**
	 * 总页码
	 */
	private int pageNum;
	
	/**
	 * 起始位置
	 */
	private int pageStart;
	
	/**
	 * 每页记录条数
	 */
	private int pageSize;
	
	/**
	 * 总记录条数
	 */
	private int pageTotal;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	
	public Page(int pageNo,int pageTotal) {
		this.pageSize=20;
		this.pageTotal=pageTotal;
		this.pageNum=getPagenum(this.pageTotal, this.pageSize);
		this.pageNo=filterNo(pageNo, pageNum);
		this.pageStart=(this.pageNo-1)*20;
	}
	
	/**
	 * 获取总页数
	 * @param pageTotal
	 * @param pageSize
	 * @return
	 */
	private int getPagenum(int pageTotal,int pageSize){
		int pageNum=pageTotal/pageSize;
		if (pageSize*pageNum<pageTotal) {
			pageNum++;
		}
		return pageNum;
	}
	
	/**
	 * 过滤分页页码
	 * @param pageNo
	 * @param pageNum
	 * @return
	 */
	private int filterNo(int pageNo,int pageNum){
		if (pageNo<=0) {
			return 1;
		} else if(pageNo>pageNum){
			return pageNum;
		}else {
			return pageNo;
		}
	}
	
}
