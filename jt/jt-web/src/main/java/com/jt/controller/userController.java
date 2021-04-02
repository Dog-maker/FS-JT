package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.HttpClientService;
import com.jt.util.CookieUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserLocalThread;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class userController {



    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    HttpClientService httpClientService;

    @Reference(check = false)
    DubboUserService dubboUserService;

    /**
     * http://www.jt.com/user/login.html
     * @param modelName
     * @return
     */
    @RequestMapping("{modelName}")
    public String login(@PathVariable String modelName){

        return modelName;
    }

    /**
     * http://www.jt.com/user/findUserList,并且要求JSON结构返回用户数据…
     */
    @RequestMapping("/findUserList")
    @ResponseBody
    public List<User> findUserList(){
        String json = httpClientService.findUserList();
        List<User> list = ObjectMapperUtil.toObject(json, List.class);
        return list;
    }

    /**
     * URL: http://www.jt.com/user/doRegister
     * 参数:password:_password,username:_username,phone:_phone
     * 返回值: SysResult对象
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user){
        dubboUserService.savaUser(user);
        return SysResult.success();
    }

    /**
     *  URL: http://www.jt.com/user/doLogin?r=0.6773414403559974
     *  返回值类型 Sysresult
     *  参数: data: {username:_username,password:_password},
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletResponse response){
        String TICKET = dubboUserService.findUserByUP(user);
        if(!StringUtils.hasLength(TICKET)){
            return SysResult.fail();        //"用户或密码为空"
        }
        CookieUtil.addCookie(response, User.TICKET_NAME,TICKET,"jt.com", "/", 7*24*60*60);
        return SysResult.success();
    }

    /**
     *  URL: http://www.jt.com/user/logout.html
     *  重定向到初始页面
     *  删除内置User对象  cookie储存信息
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //获取指定cookie
        Cookie cookie = CookieUtil.getCookie(request, User.TICKET_NAME);
        if(cookie != null){
            //获取密钥
            String Ticket = cookie.getValue();
            //从redis缓存中删除密钥信息
            if(!StringUtils.hasLength(Ticket))
                throw new RuntimeException();
            jedisCluster.del(Ticket);
            //将指定cookie清除
            CookieUtil.addCookie(response, User.TICKET_NAME, null, "jt.com", "/", 0);
        }
        //重定向到首页
        return "redirect:index";
    }


}
