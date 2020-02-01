package com.itheima.health.service;

import java.util.Map;

/**
 * @Author : wzy
 * @Description : 会员的业务接口
 * @Date : Created in 2020/1/29
 * @Version :1.0
 */
public interface MemberService {

    /**
     * 基于手机号登陆
     * @param telephone
     */
    void smsLogin(String telephone);


}
