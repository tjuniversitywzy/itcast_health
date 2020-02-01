package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : wzy
 * @Description : 检查项业务实现类
 * @Date : Created in 2020/1/25
 * @Version :1.0
 */

@Service
@Slf4j
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    @Transactional
    public void add(CheckItem checkItem) {
        log.debug("准备进行查询:{}",checkItem);
        checkItemDao.add(checkItem);

    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        log.debug("准备进行查询:{}",queryPageBean);
        //使用分页插件如何分页,在查询的时候静态分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckItem> pageList = checkItemDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(pageList.getTotal(),pageList.getResult());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        log.debug("正在进行删除操作{}",id);
        Long count = checkItemDao.countCheckItemsById(id);
        if (count>0){
            throw new RuntimeException("有关联数据，不可以删除");
        }else {
            checkItemDao.deleteCheckItemById(id);
        }
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    @Transactional
    public void edit(CheckItem checkItem) {
        log.debug("正在进行编辑操作");
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
