package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/25
 * @Version :1.0
 */
public interface CheckItemDao {
    /**
     * 新增检查项
     * @param checkItem 检查项数据
     */
    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(@Param("queryString") String queryString);

    Long countCheckItemsById(@Param("checkItemId") Integer checkItemId);

    void deleteCheckItemById(@Param("checkItemId") Integer checkItemId);

    CheckItem findById(@Param("id") Integer id);

    void edit(@Param("checkItem") CheckItem checkItem);

    List<CheckItem> findAll();
}
