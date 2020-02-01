package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author : wzy
 * @Description : 检查项业务接口
 * @Date : Created in 2020/1/25
 * @Version :1.0
 */
public interface CheckItemService {
    /**
     * 新增检查项
     * @param checkItem  表单项的检查数据
     */
    void add(CheckItem checkItem);
    //分页查询检查项列表
    PageResult pageQuery(QueryPageBean queryPageBean);

    //基于id 删除项目
    void deleteById(Integer id);

    CheckItem findById(Integer id);

    //更新检查项表单数据
    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

}
