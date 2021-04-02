package com.jt.controller;

import com.jt.util.UserLocalThread;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
