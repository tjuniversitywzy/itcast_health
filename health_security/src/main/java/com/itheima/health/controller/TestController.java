package com.itheima.health.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : wzy
 * @Description :
 * @Date : Created in 2020/1/29
 * @Version :1.0
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("/addData")
    public String addData(){
        return "addOk";
    }

    @PreAuthorize("hasAuthority('update')")
    @RequestMapping("/updateData")
    public String updateData(){
        return "updateOk";
    }

    @PreAuthorize("hasAuthority('find')")
    @RequestMapping("/findData")
    public String findData(){
        return "findOK";
    }

    @PreAuthorize("hasAuthority('delete')")
    @RequestMapping("/deleteData")
    public String deleteData(){
        return "deleteOk";
    }

}
