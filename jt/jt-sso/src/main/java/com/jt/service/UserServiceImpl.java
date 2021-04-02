package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    private static Map<Integer,String> typeMap = new HashMap<>();
    static {
        typeMap.put(1, "username");
        typeMap.put(2, "phone");
        typeMap.put(3, "email");
    }

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean checkUser(String param, Integer type) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq(typeMap.get(type), param);
        int count = userMapper.selectCount(queryWrapper);
        return count>0;
    }

    @Override
    public List<User> findUserList() {
        List<User> list = userMapper.selectList(null);
        return list;
    }
}
