package com.itheima.health.entity;

import java.io.Serializable;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/18
 * @description ：查询条件实体类
 * @version: 1.0
 */
public class QueryPageBean implements Serializable {
	private Integer currentPage;//页码
	private Integer pageSize;//每页记录数
	private String queryString;//查询条件

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	@Override
	public String toString() {
		return "QueryPageBean{" +
				"currentPage=" + currentPage +
				", pageSize=" + pageSize +
				", queryString='" + queryString + '\'' +
				'}';
	}
}
