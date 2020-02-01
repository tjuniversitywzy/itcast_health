package com.itheima.health.service;

import java.util.List;
import java.util.Map;

/**
 * @Author : wzy
 * @Description : 报表业务接口
 * @Date : Created in 2020/1/30
 * @Version :1.0
 */
public interface ReportService {

    List<Integer> findMemberCountByMonthList(List<String> months);

    /**
     * 获取运营数据
     * @return
     */
    Map<String,Object> findBusinessData();

}
