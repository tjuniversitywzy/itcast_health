package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.common.MessageConst;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.DateUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/28
 * @Version :1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;


    /**
     * 新增预约人数
     * 如果不是会员,自动注册会员
     * @param map
     * @return
     */
    @Override
    @Transactional
    public Result addOrder(Map<String, Object> map) {
        log.debug("map===>>{}",map);
        log.debug("Service层开始进行预约操作");
        //1检查预约日期是否做了预约设置
        //2.检查预约日期是否人数已满
        OrderSetting orderDate = orderSettingDao.findByOrderDate((String) map.get("orderDate"));
        if (orderDate == null){
            return new Result(false,MessageConst.GET_ORDERSETTING_FAIL);
        }
        if (orderDate.getReservations() >=orderDate.getNumber()){
            //检查预约人数是否已满
            return new Result(false, MessageConst.ORDER_FULL);
        }
        //检查是否是会员
        Member member = memberDao.findByTelephone((String) map.get("telephone"));
        if (member == null){
            //不是会员,自动注册
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date());
            member.setPhoneNumber((String)map.get("telephone"));
            memberDao.add(member);
        }else {
            //他已经成为会员了 检查此人在选定的日期,是否已经预约过,新用户不需要检查
            Order order = new Order();
            order.setMemberId(member.getId());
            try {
                order.setOrderDate(DateUtils.parseString2Date((String)map.get("orderDate")));
            }catch (Exception e){
                e.printStackTrace();
                return new Result(false,"操作失败,日期转换失败");
            }
            List<Order> orders = orderDao.selectByCondition(order);
            if (orders !=null && orders.size()>0){
                return new Result(false,MessageConst.HAS_ORDERED);
            }
        }
        //保存增加的预约订单,并更新预约人数
        //1.
        Order order4add = new Order();
        try {
            order4add.setOrderDate(DateUtils.parseString2Date((String)map.get("orderDate")));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败,日期转换失败");
        }
        order4add.setMemberId(member.getId());
        order4add.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        order4add.setOrderStatus(Order.ORDERSTATUS_NO);
        order4add.setOrderType((String)map.get("orderType"));
        //真正添加
        orderDao.add(order4add);
        orderDate.setReservations(orderDate.getReservations()+1);
        orderDao.editReservationByOrderDate(orderDate);
        return new Result(true, MessageConst.ORDER_SUCCESS,order4add.getId()+"");
    }

    @Override
    public Map<String, Object> findById4OrderDetail(Integer id) {
        log.debug("id==>>>>{}",id);
        Map<String, Object> map = orderDao.findById4OrderDetail(id);
        log.debug("map=>>{}",map);
        try {
            String dateStr = DateUtils.parseDate2String((java.sql.Date) map.get("orderDate"));
            log.debug("dateStr{}",dateStr);
            map.put("orderDate",dateStr);
        }catch (Exception e){
            e.printStackTrace();
        }

//        HashMap<String, String> map = new HashMap<>();
//        map.put("member","test");
//        map.put("setmeal","入职套餐");
//        map.put("orderDate","2020-1-30");
//        map.put("orderType","微信预约");
        return map;
    }
}
