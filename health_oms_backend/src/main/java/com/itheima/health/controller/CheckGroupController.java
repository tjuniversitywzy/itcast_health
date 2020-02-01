package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/26
 * @Version :1.0
 */

@RequestMapping("/checkgroup")
@Slf4j
@RestController
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("/add")

    public Result add(@RequestBody CheckGroup checkGroup,@RequestParam("checkItemIds") Integer[] ids){
        try {
            checkGroupService.add(checkGroup,ids);
            return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKGROUP_FAIL);
        }

    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("Controller层开始进行分页查询");
        log.debug("queryPageBean={}",queryPageBean);
        PageResult pageResult = null;
        try {
            pageResult = checkGroupService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        }catch (Exception e){
            pageResult = new PageResult(0L,new ArrayList());
        }
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        log.debug("findById{}开始了",id);
        try{
            CheckGroup group = checkGroupService.findById(id);
            return new Result(true,MessageConst.QUERY_CHECKGROUP_SUCCESS,group);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer id){
        log.debug("controller层{}",id);
        try {
            List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,"操作成功",list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,@RequestParam("checkItemIds") Integer[] checkItemsIds){
        log.debug("controller开始进行更新操作");
        log.debug("checkGroup={},ids={}",checkGroup, Arrays.toString(checkItemsIds));
        try {
            checkGroupService.edit(checkGroup, checkItemsIds);
            return new Result(true, MessageConst.EDIT_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        log.debug("Controller层开始进行查询全部");
        try {
            List<CheckGroup> all = checkGroupService.findAll();
            return new Result(true,MessageConst.QUERY_CHECKGROUP_SUCCESS,all);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("/delete")
    public Result deleteById(@RequestParam("checkgroupId") Integer id){
        try{
            log.debug("Controller层进行了删除操作");
            checkGroupService.deleteByGroupId(id);
            return new Result(true,MessageConst.DELETE_CHECKITEM_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConst.DELETE_CHECKGROUP_FAIL);
        }
    }
}
