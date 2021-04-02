package com.jt.Interceptor;

import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.GetUserUtil;
import com.jt.util.UserLocalThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class userInterceptor implements HandlerInterceptor {

    @Autowired
    JedisCluster jedisCluster;

    /**
     *  false：拦截请求
     *  true 不拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookie = CookieUtil.getCookie(request, User.TICKET_NAME);
        if(cookie != null){
            if(cookie.getValue() != null){
                if(jedisCluster.exists(cookie.getValue())){
                    User user = GetUserUtil.getUser(request, jedisCluster);
                    UserLocalThread.addUser(user);
                    return true;
                }
            }
            CookieUtil.deleteCookie(response, User.TICKET_NAME, cookie.getValue());
        }
        response.sendRedirect("/user/login.html");
        return false;
    }
}
