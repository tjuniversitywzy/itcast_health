package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.apache.ibatis.annotations.Param;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/28
 * @Version :1.0
 */
public interface MemberDao {

    /**
     * 基于手机号,查询是否有该会员
     * @param telephone
     * @return
     */
    Member findByTelephone(@Param("telephone") String telephone);

    void add(Member member);

    /**
     * 获取某月注册的会员数
     * @param month
     * @return
     */
    Integer findMemberCountByBeforeMonth(@Param("month") String month);

    /**
     * 统计会员总数量
     * @return
     */
    Integer totalMemberCount();

    /**
     * 统计某一日期会员注册数量
     * @param date
     * @return
     */
    Integer totalMemberCountByDate(@Param("date") String date);


    /**
     * 统计某一日期开始至今的会员注册人数
     * @param date
     * @return
     */
    Integer totalMemberCountAfterDate(@Param("date") String date);

}
