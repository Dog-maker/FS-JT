package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class portController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getPort")
    public String getPort(){

        return "当前端口为:"+port;
    }

}
