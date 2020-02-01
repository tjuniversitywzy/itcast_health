package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description : 预约设置dao
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */
public interface OrderSettingDao {

    //基于日期查询是否有数据
    Long countByOrderDate(@Param("date") Date date);

    //添加预约设置
    void add(OrderSetting orderSetting);

    //更新预约设置
    void update(OrderSetting orderSetting);

    /**
     * 获取区间数据
     * @param beginDate 开始
     * @param endDate   结束
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 根据预约日期,查询是否有预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(@Param("orderDate") String orderDate);
}
