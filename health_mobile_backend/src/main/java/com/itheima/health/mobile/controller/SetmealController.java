package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : wzy
 * @Description : 移动端套餐控制器
 * @Date : Created in 2020/1/28
 * @Version :1.0
 */

@RestController
@RequestMapping("/mobile/setmeal")
@Slf4j
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmealList")
    public Result getSetmealList(){
        try {
            List<Setmeal> all = setmealService.findAll();
            return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS,all);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            Setmeal byId = setmealService.findById(id);
            return new Result(true,MessageConst.QUERY_SETMEALLIST_SUCCESS,byId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_SETMEALLIST_FAIL);
        }
    }
}
