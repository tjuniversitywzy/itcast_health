package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * @Author : wzy
 * @Description : 套餐控制器
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file){
        try{
            //原名称
            String originalFilename = file.getOriginalFilename();
            log.debug("getOriginalFilename===>>>>>{}",originalFilename);
            String saveUploadName = UUID.randomUUID().toString().replace("-","")+"_"+originalFilename;
            //七牛上传
            QiniuUtils.upload2Qiniu(file.getBytes(),saveUploadName);
            //把文件名存入redis，基于redis的set集合
            jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_RESOURCES,saveUploadName);
            //把上传之后，可以下载读取的连接下发到客户端
            String filePath = QiniuUtils.qiniu_img_url_pre+saveUploadName;
            log.debug("filepath===>>>>>{}",file);
            return new Result(true, MessageConst.PIC_UPLOAD_SUCCESS,filePath);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") Integer[] checkGroupIds){
        try {
            //处理一下图片名字，再传到数据库内
            String img = setmeal.getImg();
            String saveName = img.replace(QiniuUtils.qiniu_img_url_pre, "");
            log.debug("全路径：{}",img);
            log.debug("储存在数据库中的{}",saveName);
            setmeal.setImg(saveName);
            setmealService.add(setmeal, checkGroupIds);
            return new Result(true,MessageConst.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = null;
        try {
            pageResult = setmealService.pageQuery(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
            pageResult = new PageResult(0L,new ArrayList());
        }
        return pageResult;
    }
}
