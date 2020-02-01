package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description : 套餐业务接口
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */
public interface SetmealService {

    //新增套餐

    /**
     *
     * @param setmeal 套餐表单数据
     * @param checkGroupIds 套餐选中检查组列表
     */
    void add(Setmeal setmeal,Integer[] checkGroupIds);

    /**
     *
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String,Object>> findSetmealCount();
}
