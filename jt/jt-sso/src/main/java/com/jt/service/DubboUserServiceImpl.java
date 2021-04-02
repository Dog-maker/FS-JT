package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService{

    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    UserMapper userMapper;

    @Override
    public void savaUser(User user) {
        byte[] password = user.getPassword().getBytes();
        String newPassword = DigestUtils.md5DigestAsHex(password);
        user.setPassword(newPassword).setEmail(user.getPhone());
        userMapper.insert(user);
    }

    @Override
    public String findUserByUP(User user) {
        //1.校验用户名和密码
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper(user);   //规则，将user对象中不为空的属性当做where条件
        User newUser = userMapper.selectOne(queryWrapper);
        //1.1不存在直接返回null
        if (newUser == null)
            return null;
        //1.2存在的话动生成秘钥，将用户信息转化成json
        String Ticket = String.valueOf(UUID.randomUUID()).replace("-", "");
        //转化json之前脱敏
        newUser.setPassword("aaaa");
        String json = ObjectMapperUtil.toJson(newUser);
        //2.信息持久化在redis
        jedisCluster.setex(Ticket, 7*24*60*60, json);
        //3.返回秘钥信息
        return Ticket;
    }


}
