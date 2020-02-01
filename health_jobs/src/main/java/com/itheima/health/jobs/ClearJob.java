package com.itheima.health.jobs;

import com.itheima.health.common.RedisConst;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/27
 * @Version :1.0
 */
public class ClearJob {

    @Autowired
    private JedisPool jedisPool;
    public void clearImgJob(){
        System.out.println("清除图片");
    }


    public void clearImageJob(){
        Set<String> sdiffSet = jedisPool.getResource().sdiff(RedisConst.SETMEAL_PIC_RESOURCES, RedisConst.SETMEAL_PIC_DB_RESOURCES);
        for (String name : sdiffSet){
            System.out.println(name);
            QiniuUtils.deleteFileFromQiniu(name);
            //还要把redis中的内容删除
            jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES,name);
        }
    }
}
