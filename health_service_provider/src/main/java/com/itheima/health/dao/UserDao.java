package com.itheima.health.dao;

import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/30
 * @Version :1.0
 */
public interface UserDao {

    User findByUsername(@Param("username") String username);
}
