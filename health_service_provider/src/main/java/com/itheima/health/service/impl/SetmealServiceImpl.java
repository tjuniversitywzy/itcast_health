package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.common.RedisConst;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * @Author : wzy
 * @Description : 套餐业务实现类
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private OrderDao orderDao;
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        log.debug("Service层正在进行套餐组添加工作");
        log.debug("setmeal====>>{}",setmeal);
        log.debug("checkgroupids====>>{}", Arrays.toString(checkGroupIds));
        setmealDao.add(setmeal);
        for (Integer id:checkGroupIds){
            HashMap<String, Integer> map = new HashMap<>();
            map.put("setmeal_id",setmeal.getId());
            map.put("checkgroup_id",id );
            setmealDao.addSetmealAndCheckGroup(map);
        }
        jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> pages = setmealDao.pageQuery(queryPageBean.getQueryString());
        return new PageResult(pages.getTotal(),pages.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * 基于id获取套餐信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        log.debug("Service层正在进行根据id查询操作{}",id);
//        Setmeal byId = setmealDao.findById(id);
        Setmeal byId = setmealDao.findId(id);
        log.debug("byId = {}",byId);
        return byId;
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
//        HashMap<String, Object> map1 = new HashMap<>();
//        map1.put("name","套餐1");
//        map1.put("value",10);
//
//        HashMap<String, Object> map2 = new HashMap<>();
//        map2.put("name","套餐2");
//        map2.put("value",20);
//
//        HashMap<String, Object> map3 = new HashMap<>();
//        map3.put("name","套餐3");
//        map3.put("value",30);
//
//        ArrayList<Map<String, Object>> list = new ArrayList<>();
//        list.add(map1);
//        list.add(map2);
//        list.add(map3);
        List<Map<String, Object>> setmealCount = orderDao.findSetmealCount();

        return setmealCount;
    }
}
