package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */
public interface SetmealDao {

    void add(Setmeal setmeal);

    void addSetmealAndCheckGroup(Map<String,Integer> map);

    Page<Setmeal> pageQuery(@Param("queryString") String queryString);

    //获取所有套餐列表
    List<Setmeal> findAll();

    Setmeal findById(@Param("id") Integer id);

    Setmeal findId(@Param("id") Integer id);
}
