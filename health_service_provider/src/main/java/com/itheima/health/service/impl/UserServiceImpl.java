package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/7
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = UserService.class)
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public boolean login(String username, String password) {
		log.debug("service_provide...u:{},p:{}",username,password);
		if("admin".equals(username) && "123".equals(password)){
			return true;
		}
		return false;
	}

	/**
	 * 根据用户名寻找用户，并且把用户角色、权限一并赋值进去
	 * @param username
	 * @return
	 */
	@Override
	public User findByUserName(String username) {
		User user1 = userDao.findByUsername(username);
		Set<Role> user1_role = roleDao.findByUserId(user1.getId());
		for (Role role : user1_role){
			Set<Permission> user1_role_permission = permissionDao.findByRoleId(role.getId());
			role.setPermissions(user1_role_permission);
		}
		user1.setRoles(user1_role);
		//我这个地方根视频中的写倒了,应该是一样的，因为是地址
		return user1;
	}
}
