package com.itheima.health.dao;

import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/28
 * @Version :1.0
 */
public interface OrderDao {
    /**
     * 多条件组合查询
     * @param order 查询条件封装到对象
     * @return
     */
    List<Order> selectByCondition(Order order);

    /**
     * 增加订单信息
     * @param order
     */
    void add(Order order);

    /**
     * 根据日期时间更新已预约人数
     * @param orderSetting
     */
    void editReservationByOrderDate(OrderSetting orderSetting);

    /**
     * 基于订单id,获取订单详情
     * @param id
     * @return
     */
    Map<String,Object> findById4OrderDetail(@Param("id") Integer id);

    /**
     * 获取所有套餐 占比
     * @return
     */
    List<Map<String,Object>> findSetmealCount();

    /**
     * 统计预约总数
     * @return
     */
    Integer totalOrderCount();

    /**
     * 统计某一日期预约人数
     * @param date
     * @return
     */
    Integer totalOrderByDate(@Param("date") String date);

    Integer totalVisitByDate(@Param("date") String date);

    Integer totalOrderByAfterDate(@Param("date") String date);

    Integer totalVisitByAfterDate(@Param("date") String date);

    /**
     * 获取热门套餐列表
     * @return
     */
    List<Map<String,Object>> getHotSetmeal();
}
