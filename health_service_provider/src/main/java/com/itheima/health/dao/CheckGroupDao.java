package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/26
 * @Version :1.0
 */

public interface CheckGroupDao {

    //新增检查组的内部信息
    void add(CheckGroup checkGroup);

    //新增检查组和检查项的内部关系
    void addCheckGroupAndCheckItem(Map oneMap);

    Page<CheckGroup> selectByCondition(@Param("queryString") String queryString);

    //根据id获取检查组
    CheckGroup findById(@Param("id") Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(@Param("id") Integer id);

    //更新检查组基本信息
    void edit(CheckGroup checkGroup);

    //删除关系标
    void deleteCheckItemIdsByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);

    List<CheckGroup> findAll();

    void deleteCheckGroupByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);
}
