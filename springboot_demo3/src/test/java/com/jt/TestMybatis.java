package com.jt;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.mapper.userMapper;
import com.jt.pojo.User;

@SpringBootTest
public class TestMybatis {

    @Autowired
    private userMapper userMapper;

    @Test
    public void testFindUser() {
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setName("刘").setAge(18).setSex("男");
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void select() {
        User user;
        user = userMapper.selectById(21);
        System.out.println(user);
        int count = userMapper.selectCount(null);
        System.out.println(count);
    }

    /**
     * 条件构造器
     * 逻辑运算符
     * eq:=   gt:>     lt:<
     * ge:>=       le:<=
     */
    @Test
    public void select01() {
        //性别为女  年龄大于100
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex", "女")
                .le("age", 100);
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    /**
     * 查询 id在 1,3,5,7,9里面的
     * in(selectBatchIds)    or
     */
    @Test
    public void select02() {
        List<Integer> ids = new ArrayList<>();
//        ids.add(1);
//        ids.add(3);
//        ids.add(5);
//        ids.add(7);
//        ids.add(9);
        Integer[] Ids = {1, 3, 5, 7, 9};
        ids = Arrays.asList(Ids);
        List<User> users = userMapper.selectBatchIds(ids);
        System.out.println(users);
    }

    @Test
    public void operation() {
        User user = new User();
        user.setName("小白").setSex("男").setAge(1800).setId(55);
        userMapper.updateById(user);
    }


}

