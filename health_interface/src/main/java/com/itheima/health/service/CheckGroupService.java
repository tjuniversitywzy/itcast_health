package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @Author : wzy
 * @Description :检查组业务接口
 * @Date : Created in 2020/1/26
 * @Version :1.0
 */
public interface CheckGroupService {

    /**
     * 新增检查组
     * @param checkGroup 检查组表单数据
     * @param checkItemIds 检查项选择的id列表
     */
    void add(CheckGroup checkGroup,Integer[] checkItemIds);

    PageResult pageQuery(Integer currentPage,Integer pageSize ,String queryString);

    /**
     * 根据id获取检查组id
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    //编辑检查组
    void edit(CheckGroup checkGroup,Integer[] checkItemIds);

    List<CheckGroup> findAll();

    void deleteByGroupId(Integer id);
}
