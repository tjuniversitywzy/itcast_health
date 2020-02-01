package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.dev.ReSave;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description : 预约设置控制器
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */
@RestController
@RequestMapping("/ordersetting")
@Slf4j
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;
    /**
     * 批量接收上传的Excel
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file){
        try {
            log.debug("excel fileName:{}",file.getOriginalFilename());
            ArrayList<OrderSetting> lists = new ArrayList<>();
            List<String[]> list = POIUtils.readExcel(file);
            for (String[] row:list){
                log.debug("@@@ excel row[0]={},row[1]={}",row[0],row[1]);
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(row[0]));
                orderSetting.setNumber(Integer.parseInt(row[1]));
                lists.add(orderSetting);
            }
            orderSettingService.add(lists);
            return new Result(true, MessageConst.IMPORT_ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrdersettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> maps = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConst.GET_ORDERSETTING_SUCCESS,maps);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        log.debug("orderSetting={}",orderSetting);
        try {
            orderSettingService.editOrderSettingByDate(orderSetting);
            return new Result(true,"操作成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

}
