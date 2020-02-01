package com.itheima.health.mobile.controller;

import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.mobile.utils.SMSUtils;
import com.itheima.health.mobile.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.swing.text.TabExpander;

/**
 * @Author : wzy
 * @Description : 验证码控制器
 * @Date : Created in 2020/1/28
 * @Version :1.0
 */
@RestController
@Slf4j
@RequestMapping("/mobile/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        try {
            log.debug("phone{}",telephone);
            String validateCode = ValidateCodeUtils.generateValidateCode(4).toString();
            log.debug("validateCode:{}",validateCode);
            //发送三方验证码
//            SMSUtils.sendShortMessage(telephone,validateCode);
            jedisPool.getResource().setex(telephone+RedisConst.SENDTYPE_ORDER, 5*60, validateCode);
            return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){

            e.printStackTrace();
            return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            //第三方发送验证码
//            SMSUtils.sendShortMessage(telephone,code);
            log.debug("验证码{}",code);
            jedisPool.getResource().setex(telephone+RedisConst.SENDTYPE_LOGIN,300,code);
            return new Result(true,MessageConst.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.SEND_VALIDATECODE_FAIL);
        }
    }
}
