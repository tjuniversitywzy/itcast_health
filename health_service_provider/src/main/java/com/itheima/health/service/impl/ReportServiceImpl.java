package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.service.ReportService;
import com.itheima.health.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/30
 * @Version :1.0
 */

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;


    @Override
    public List<Integer> findMemberCountByMonthList(List<String> months) {
        log.debug("月份信息：{}",months);
        ArrayList<Integer> list = new ArrayList<>();
        for (String month : months){
            Integer count = memberDao.findMemberCountByBeforeMonth(month+".31");
            list.add(count);
        }
        return list;
    }

    @Override
    public Map<String, Object> findBusinessData() {

        HashMap<String, Object> report = new HashMap<>();
        Integer totalMember = memberDao.totalMemberCount();

        try {
            Integer todayNewMember = memberDao.totalMemberCountByDate(DateUtils.parseDate2String(DateUtils.getToday()));
            Integer thisWeekNewMember = memberDao.totalMemberCountAfterDate(DateUtils.parseDate2String(DateUtils.getFirstDayOfWeek(new Date())));
            Integer thisMonthNewMember = memberDao.totalMemberCountAfterDate(DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth()));
            String reportDate = DateUtils.parseDate2String(new Date());
            report.put("reportDate",reportDate);
            report.put("todayNewMember",todayNewMember);
            report.put("thisWeekNewMember",thisWeekNewMember);
            report.put("thisMonthNewMember",thisMonthNewMember);
            report.put("totalMember",totalMember);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("会员日期格式转换存在问题");

        }

        try {
            Integer todayOrderNumber = orderDao.totalOrderByDate(DateUtils.parseDate2String(new Date()));
            Integer thisWeekOrderNumber = orderDao.totalOrderByAfterDate(DateUtils.parseDate2String(DateUtils.getThisWeekMonday()));
            Integer thisMonthOrderNumber = orderDao.totalOrderByAfterDate(DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth()));
            report.put("todayOrderNumber",todayOrderNumber);
            report.put("thisWeekOrderNumber",thisWeekOrderNumber);
            report.put("thisMonthOrderNumber",thisMonthOrderNumber);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("预约日期格式转换存在问题");
        }

        try {
            Integer todayVisitsNumber = orderDao.totalVisitByDate(DateUtils.parseDate2String(DateUtils.getToday()));
            Integer thisWeekVisitsNumber = orderDao.totalVisitByAfterDate(DateUtils.parseDate2String(DateUtils.getThisWeekMonday()));
            Integer thisMonthVisitsNumber = orderDao.totalVisitByAfterDate(DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth()));
            log.debug("todayVisitsNumber:{}",todayVisitsNumber);
            log.debug("thisWeekVisitsNumber:{}",thisWeekVisitsNumber);
            log.debug("thisMonthVisitsNumber:{}",thisMonthVisitsNumber);

            report.put("todayVisitsNumber",todayVisitsNumber);
            report.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            report.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("到诊日期格式转换存在问题");
        }

        List<Map<String, Object>> hotSetmeal = orderDao.getHotSetmeal();
        report.put("hotSetmeal",hotSetmeal);

        return report;

    }
}
