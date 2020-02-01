package com.itheima.health.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/7
 * @description ：返回结果类
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
	private boolean flag;	//执行结果，true为执行成功 false为执行失败
	private String message;	//返回结果信息是否失败之类的
	private Object data;	//返回数据
	public Result(boolean flag,String message){
		this(flag,message,null);
	}
}
