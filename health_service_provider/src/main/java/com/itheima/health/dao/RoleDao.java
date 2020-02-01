package com.itheima.health.dao;

import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/30
 * @Version :1.0
 */
public interface RoleDao {

    Set<Role> findByUserId(@Param("userId") Integer userId);
}
