package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/")
public class userController {

    @Autowired
    private userService userService;

    @RequestMapping("findAll")
    public String findAll(Model model){
        List<User> userList = userService.findAll();
        model.addAttribute(userList);
        return "userList";
    }


    @RequestMapping("Ajax")
    @ResponseBody
    public List findAll(){
        return userService.findAll();
    }

    @RequestMapping("toAjax")
    public String toAjax(){
        return "ajax";
    }

}
