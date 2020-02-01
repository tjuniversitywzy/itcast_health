package com.itheima.health.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/18
 * @description ：分页结果实体类
 * @version: 1.0
 */
public class PageResult implements Serializable {
	private Long total;//总记录数
	private List rows;//当前页结果
	public PageResult(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
