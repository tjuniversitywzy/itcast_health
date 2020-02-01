package com.itheima.health.service;

import com.itheima.health.pojo.User;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/7
 * @description ：
 * @version: 1.0
 */
public interface UserService {
	public boolean login(String username,String password);

	/**
	 * 基于用户名获取用户信息
	 * @return
	 */
	User findByUserName(String username);
}
