package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    //获取cookie对象
    public static Cookie getCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length>0){
            for (Cookie cookie: cookies){
                if(cookieName.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }

    //根据request和name获取获取cookie的值
    public static String getCookieValue(HttpServletRequest request,String cookieName){
        Cookie cookie = CookieUtil.getCookie(request, cookieName);
        return cookie ==null?null:cookie.getValue();
    }

    //根据Cookie获取cookie的值
    public static String getCookieValue(Cookie cookie){
        return cookie.getValue();
    }

    //新增Cookie
    public static void addCookie(HttpServletResponse response ,String CookieName,String CookieValue,String domain,String path,Integer seconds){
        Cookie cookie = new Cookie(CookieName, CookieValue);
        cookie.setPath(path);
        cookie.setDomain(domain);
        cookie.setMaxAge(seconds);
        response.addCookie(cookie);
    }

    //新增Cookie
    public static void deleteCookie(HttpServletResponse response ,String CookieName,String CookieValue){
        Cookie cookie = new Cookie(CookieName, CookieValue);
        cookie.setPath("/");
        cookie.setDomain("com.jt");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
