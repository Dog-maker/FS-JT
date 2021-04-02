package com.jt.service;

import com.jt.mapper.userMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements userService{

    @Autowired
    private userMapper userMapper;


    public List<User> findAll() {

        return userMapper.selectList(null);
    }
}
