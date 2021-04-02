package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class userController {

    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    UserService userService;

    /**
     * url:http://sso.jt.com/user/check/lijiahao/1?r=0.8418040935976443&callback=jsonp1616673598858&_=1616673611430
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject check(@PathVariable String param, @PathVariable Integer type,String callback){
        //System.out.println(param+"*****"+type);
        boolean flag = userService.checkUser(param,type);
        return new JSONPObject(callback, SysResult.success(flag));
    }

    /**
     * 通过http://sso.jt.com/user/findUserList,并且要求JSON结构返回用户数据…
     * @return
     */
    @RequestMapping("/findUserList")
    public List<User> findUserList(){
        return userService.findUserList();
    }


    @RequestMapping("/testMSG")
    public String testMSG(){
        return "单点登录测试";
    }

    /**
     *  http://sso.jt.com/user/query/7b7e90959a9149c48e2ebaaeef8e7151?callback=jsonp1617014090837&_=1617014090904
     *  参数 ticket callback
     */
    @RequestMapping("/query/{Ticket}")
    public JSONPObject findUserByTicket(@PathVariable String Ticket, String callback){
        if(jedisCluster.exists(Ticket)){
            String json = jedisCluster.get(Ticket);
            return new JSONPObject(callback, SysResult.success(json));
        }else {
            return new JSONPObject(callback, SysResult.fail());
        }
    }
}
