package com.gls.search.util;

import java.util.List;

public class Page<T> {
	
	    //商品列表
		private List<T> objects;
		//总记录数
		private Integer counts;
		//总页数
		private Integer pageCount;
		//当前页
		private Integer curPage;
		//页面记录数
		private Integer pageSize;
		
		public Page(){
			pageSize = 40;
			curPage = 1;
		}

		public List<T> getObjects() {
			return objects;
		}

		public void setObjects(List<T> objects) {
			this.objects = objects;
		}

		public Integer getCounts() {
			return counts;
		}

		public void setCounts(Integer counts) {
			this.counts = counts;
		}

		public Integer getPageCount() {
			return pageCount;
		}

		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}

		public Integer getCurPage() {
			return curPage;
		}

		public void setCurPage(Integer curPage) {
			this.curPage = curPage;
		}

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		
		
}
