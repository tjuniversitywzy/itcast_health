package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/30
 * @Version :1.0
 */
@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    @Reference
    private SetmealService setmealService;
    /**
     * 获取前12个月，每月累计的注册人数
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){

        try {
            ArrayList<String> monthList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-12);
            SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy.MM");
            for (int i = 0;i!=12;i++){
                calendar.add(Calendar.MONTH,1);
                monthList.add(simpleDateFormat.format(calendar.getTime()));
                System.out.println(simpleDateFormat.format(calendar.getTime()));

            }
            //构建月份列表
            List<Integer> memberCounts = reportService.findMemberCountByMonthList(monthList);
            HashMap<String, Object> map = new HashMap<>();
            map.put("months",monthList);
            map.put("memberCount", memberCounts);
            return new Result(true, "操作成功",map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }


    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try {
            //通过service获取套餐占比
            List<Map<String, Object>> mapList = setmealService.findSetmealCount();

            ArrayList<String> nameList = new ArrayList<>();
            for (Map<String,Object> map : mapList){
                nameList.add((String) map.get("name"));
            }
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("setmealNames",nameList);
            resultMap.put("setmealCount",mapList);
            return new Result(true,"操作成功",resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReport(){
        try {
            Map<String, Object> businessData = reportService.findBusinessData();
            return new Result(true,"操作成功",businessData);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"操作失败");
        }

    }


    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessData(HttpServletResponse response){
        try {
            // 获取报表数据
            Map<String,Object> result = reportService.findBusinessData();
            System.out.println("result:"+result);
            // 取出返回结果
            String orderDate = (String)result.get("reportDate");
            // 获取会员相关数据
            Integer todayNewMember = (Integer)result.get("todayNewMember");
            Integer thisWeekNewMember = (Integer)result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer)result.get("thisMonthNewMember");
            Integer totalMember = (Integer)result.get("totalMember");
            // 获取预约相关数据
            Integer todayOrderNumber = (Integer)result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer)result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer)result.get("thisMonthOrderNumber");
            // 获取到诊相关数据
            Integer todayVisitsNumber = (Integer)result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer)result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer)result.get("thisMonthVisitsNumber");
            // 获取套餐数据
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            // 利用POI，把数据写入Excel文件
            // 获取excel文件输入流
            InputStream excelFileInputStream = this.getClass().getResourceAsStream("/report_template.xlsx");
            // 构建Workbook对象，获取工作表
            XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 获取报表日期行
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(orderDate);

            // 获取新增会员及会员总数行
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            // 获取本周新增会员及本月新增会员行
            row = sheet.getRow(5);

            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            // 获取今日预约及到诊数行
            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);

            // 获取本周预约及本周到诊行
            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            // 获取本月预约及本月到诊行
            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            int rowStartNum = 12;
            for(Map map :hotSetmeal){
                String name = (String)map.get("name");
                Long setmealCount = (Long)map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowStartNum);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmealCount);
                row.getCell(6).setCellValue(proportion.doubleValue());
                rowStartNum++;
            }

            //把流返回给客户端
            ServletOutputStream outputStream = response.getOutputStream();
            //形成下载的效果
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition","attachment;filename="+result.get("reportDate")+"_report.xlsx");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }
}
