package com.itheima.health.service;

import com.itheima.health.entity.Result;

import java.util.Map;

/**
 * @Author : wzy
 * @Description : 订单业务接口
 * @Date : Created in 2020/1/28
 * @Version :1.0
 */
public interface OrderService {
    /**
     * 添加订单
     * @param map
     * @return
     */
    Result addOrder(Map<String,Object> map);


    Map<String,Object> findById4OrderDetail(Integer id);
}
