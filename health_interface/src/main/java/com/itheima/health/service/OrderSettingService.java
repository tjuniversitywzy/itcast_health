package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description : 预约设置业务接口
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */
public interface OrderSettingService {
    //批量导入
    void add(List<OrderSetting> orderSettingList);

    /**
     * 获取某月预约设置
     * @param date yyyy-mm
     * @return
     */
    List<Map> getOrderSettingByMonth(String date);

    /**
     * 基于日期更新或者新增预约日期
     * @param orderSetting
     */
    void editOrderSettingByDate(OrderSetting orderSetting);
}
