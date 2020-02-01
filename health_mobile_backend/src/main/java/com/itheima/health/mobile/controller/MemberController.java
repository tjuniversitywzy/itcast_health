package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/29
 * @Version :1.0
 */
@RestController
@Slf4j
@RequestMapping("/mobile/member")
public class MemberController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("/smsLogin")
    public Result smsLogin(@RequestBody Map<String,String> map){
        try {
            String telephone = map.get("telephone");
            log.debug("Controller层的电话：{}",telephone);
            String code = map.get("validateCode");
            //从jedis中校验验证码：
            String codeInRedis = jedisPool.getResource().get(telephone+ RedisConst.SENDTYPE_LOGIN);
            if (codeInRedis == null || !codeInRedis.equals(code)){
                return new Result(false, MessageConst.VALIDATECODE_ERROR);
            }
            //调用service，完成登陆操作
            memberService.smsLogin(telephone);
            return new Result(true,MessageConst.LOGIN_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"登陆失败");
        }
    }

}
