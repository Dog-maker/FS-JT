package com.jt.util;

import com.jt.pojo.User;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class GetUserUtil {

    ;


    public static User getUser(HttpServletRequest request,JedisCluster jedisCluster){
        //1.获取用户信息-id
        //1.1获取cookie信息
        Cookie cookie = CookieUtil.getCookie(request, User.TICKET_NAME);
        if (cookie == null){
            return null;
        }
        //1.2根据ticket获取用户信息
        String JSON = jedisCluster.get(cookie.getValue());
        User user = ObjectMapperUtil.toObject(JSON, User.class);
        return user;
    }

    public static Integer getUserId(HttpServletRequest request,JedisCluster jedisCluster){
        return GetUserUtil.getUser(request, jedisCluster).getId();
    }


}
