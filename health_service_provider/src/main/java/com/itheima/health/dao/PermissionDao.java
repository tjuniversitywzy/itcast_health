package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/30
 * @Version :1.0
 */
public interface PermissionDao {

    Set<Permission> findByRoleId(@Param("roleId") Integer roleId);
}
