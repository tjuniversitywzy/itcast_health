package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */

@Service
@Slf4j
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        //遍历数据,插入或者更新数据
        log.debug(">>>>ordersettingList={}",orderSettingList);
        for (OrderSetting orderSetting : orderSettingList){
            Long count = orderSettingDao.countByOrderDate(orderSetting.getOrderDate());
            if (count > 0L){
                orderSettingDao.update(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }

    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        log.debug("Service层正在进行获取操作");
        ArrayList<Map> list = new ArrayList<>();
        String beginDate = date.concat("-1");
        String endDate = date.concat("-31");
        List<OrderSetting> orderSettingByMonth = orderSettingDao.getOrderSettingByMonth(beginDate,endDate);
        for (OrderSetting orderSetting : orderSettingByMonth){
            HashMap<Object, Object> oneMap = new HashMap<>();
            oneMap.put("date",orderSetting.getOrderDate().getDate());
            oneMap.put("number",orderSetting.getNumber());
            oneMap.put("reservations",orderSetting.getReservations());
            list.add(oneMap);
        }
        return list;
    }

    @Override
    public void editOrderSettingByDate(OrderSetting orderSetting) {
        log.debug("orderSetting{}",orderSetting);
        Long count = orderSettingDao.countByOrderDate(orderSetting.getOrderDate());
        if (count > 0){
            orderSettingDao.update(orderSetting);
        }else {
            orderSetting.setReservations(0);
            orderSettingDao.add(orderSetting);
        }

    }


}
