package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.mobile.utils.SMSUtils;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/28
 * @Version :1.0
 */
@RestController
@Slf4j
@RequestMapping("/mobile/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map<String,Object> map){
        try {
            //验证短信验证码
            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");
            String code_jedis = jedisPool.getResource().get(telephone+ RedisConst.SENDTYPE_ORDER);
            if (code_jedis ==null || !code_jedis.equals(validateCode)){
                return new Result(false, MessageConst.VALIDATECODE_ERROR);
            }
            //这里可以通过服务端告知是什么途径预约的
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            //调用Service
            Result result = orderService.addOrder(map);
            log.debug("发送短信通知预约人");
            //发送通知
//            SMSUtils.sendShortMessage(telephone,);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"预约失败");
        }
    }

    @RequestMapping("/findById")
    public Result findById4OrderDetail(Integer id){
        try{
            Map<String, Object> map = orderService.findById4OrderDetail(id);
            return new Result(true,MessageConst.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_ORDER_FAIL);
        }
    }
}
