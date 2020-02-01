package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/29
 * @Version :1.0
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Override
    public void smsLogin(String telephone) {
        Member member = memberDao.findByTelephone(telephone);

        if (member == null){
            log.debug("这个人不是会员，准备开始登陆");
            Member memberNew = new Member();
            memberNew.setPhoneNumber(telephone);
            memberNew.setRegTime(new Date());
            memberDao.add(memberNew);
        }

    }

}
