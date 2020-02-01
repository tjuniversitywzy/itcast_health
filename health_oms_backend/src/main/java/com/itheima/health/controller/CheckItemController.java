package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : wzy
 * @Description : 业务检查项控制器
 * @Date : Created in 2020/1/25
 * @Version :1.0
 */

@RestController
@Slf4j
@RequestMapping("/checkitem")
public class CheckItemController {
    //远程调用Service
    @Reference
    private CheckItemService checkItemService;
    /**
     * 新增检查项web方法
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            log.debug("CheckItemController:{}",checkItem);
            checkItemService.add(checkItem);
            return new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage")
    public PageResult pageResult(@RequestBody QueryPageBean queryPageBean){
        try {
            log.debug("CheckItemController:{}",queryPageBean);
            return checkItemService.pageQuery(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("换页的时候出现问题了");
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            log.debug("CheckItemController:{}",id);
            checkItemService.deleteById(id);
            return new Result(true,MessageConst.DELETE_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try{
            log.debug("id={}",id);
            CheckItem item = checkItemService.findById(id);
            if (item != null){
                return new Result(true,MessageConst.QUERY_CHECKITEM_SUCCESS,item);
            }else {
                return new Result(false,MessageConst.QUERY_CHECKITEM_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            log.debug("checkItem{}",checkItem);
            checkItemService.edit(checkItem);
            return new Result(true,MessageConst.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.EDIT_CHECKITEM_FAIL);
        }
    }
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            log.debug("开始查询全部人员");
            List<CheckItem> all = checkItemService.findAll();
            return new Result(true,MessageConst.QUERY_CHECKITEM_SUCCESS,all);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }
}
