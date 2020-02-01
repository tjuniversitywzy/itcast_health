package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/26
 * @Version :1.0
 */

@Service
@Slf4j
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.add(checkGroup);
        log.debug("checkGroup.getId {}",checkGroup.getId());
        for (Integer checkItemId:checkItemIds){
            //遍历选中的检查项列表，逐个添加到检查组检查项
            Map<String,Integer> oneMap = new HashMap<String, Integer>();
            oneMap.put("checkgroup_id",checkGroup.getId());
            oneMap.put("checkitem_id",checkItemId);
            checkGroupDao.addCheckGroupAndCheckItem(oneMap);
        }
    }

    /**
     * 分页查询
     * @param currentPage  当前页数
     * @param pageSize     总页数
     * @param queryString  查询条件
     * @return
     */
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        log.debug("Service层开始进行分页操作！！！");
        log.debug("current={},pagesize={},queryString={}",currentPage,pageSize,queryString);
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> checkGroups = checkGroupDao.selectByCondition(queryString);
        return new PageResult(checkGroups.getTotal(),checkGroups.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        log.debug("findById{}",id);
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        log.debug("findCheckItemIdsByCheckGroupId查询id{}",id);
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    @Transactional
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        log.debug("Service开始进行更新操作");

        checkGroupDao.edit(checkGroup);
        //先删除关系，再添加关系
        checkGroupDao.deleteCheckItemIdsByCheckGroupId(checkGroup.getId());
        //新增关联关系
        for (Integer checkItemId:checkItemIds){
            HashMap<String, Integer> oneMap = new HashMap<>();
            oneMap.put("checkgroup_id",checkGroup.getId());
            oneMap.put("checkitem_id",checkItemId);
            checkGroupDao.addCheckGroupAndCheckItem(oneMap);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        log.debug("Service层进行了数据查询");
        List<CheckGroup> all = checkGroupDao.findAll();
        return all;
    }

    /**
     * 根据检查组id删除检查组
     * @param id 传入id
     */
    @Override
    @Transactional
    public void deleteByGroupId(Integer id) {
        log.debug("Service层进行了删除操作---id={}",id);
        checkGroupDao.deleteCheckItemIdsByCheckGroupId(id);
        checkGroupDao.deleteCheckGroupByCheckGroupId(id);
    }
}
