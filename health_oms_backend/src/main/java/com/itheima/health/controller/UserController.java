package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/7
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @RequestMapping("/loginSuccess")
    public Result loginSuccess(){
        return new Result(true,MessageConst.LOGIN_SUCCESS);
    }

    @RequestMapping("/loginFail")
    public Result loginFail(){
        return new Result(false,"登陆失败");
    }

    @RequestMapping("/getUsername")
    public Result getUsername(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() == null){
                return new Result(false,"获取用户名失败");
            }
            User user = (User)authentication.getPrincipal();
            return new Result(true,"获取成功",user.getUsername());

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"获取用户名失败");
        }
    }
}
