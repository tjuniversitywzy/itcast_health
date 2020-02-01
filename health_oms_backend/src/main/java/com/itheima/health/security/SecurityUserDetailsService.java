package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/22
 * @description ：
 * @version: 1.0
 */
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// 模拟数据库的用户记录，如下User类是health_common中的自定义实体类User
	// 修改Role、Permission，为其增加不带参、带参构造方法
//	private static Map<String, User> userDb = new HashMap();
//	static {
//		User user1 = new User();
//		user1.setUsername("admin");
//		user1.setPassword("123");
//		// 用户权限与角色
//		Role role1 = new Role("系统管理员","ROLE_ADMIN");
//		role1.getPermissions().add(new Permission("添加权限","add"));
//		role1.getPermissions().add(new Permission("删除权限","delete"));
//		role1.getPermissions().add(new Permission("更新权限","update"));
//		role1.getPermissions().add(new Permission("查询权限","find"));
//		user1.getRoles().add(role1);
//		userDb.put(user1.getUsername(),user1);
//
//		User userZhangSan = new User();
//		userZhangSan.setUsername("zhangsan");
//		userZhangSan.setPassword("123");
//		Role role2 = new Role("数据分析员","ROLE_READER");
//		role2.getPermissions().add(new Permission("查询权限","find"));
//		userZhangSan.getRoles().add(role2);
//		userDb.put(userZhangSan.getUsername(),userZhangSan);
//
//		User userLisi = new User();
//		userLisi.setUsername("lisi");
//		userLisi.setPassword("123");
//		Role role3 = new Role("运营管理员","ROLE_OMS");;
//		role3.getPermissions().add(new Permission("添加权限","add"));
//		role3.getPermissions().add(new Permission("更新权限","update"));
//		userLisi.getRoles().add(role3);
//		userDb.put(userLisi.getUsername(),userLisi);
//	}
	@Reference
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUserName(username);
		if (user == null){
			System.out.println("用户失败");
			return null;
		}
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role:user.getRoles()){
			//把角色关键词放入列表
			authorities.add(new SimpleGrantedAuthority(role.getKeyword()));
			for (Permission permission:role.getPermissions()){
				//把权限关键词放入列表
				authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
			}
		}
		//封装userDetail
		//如果数据库的密码已经按照bCryptPasswordEncoder加密，就直接使用就可以了
		String authPassword = user.getPassword();
		System.out.println(authPassword);
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, authPassword, authorities);
		return userDetails;
	}
}
