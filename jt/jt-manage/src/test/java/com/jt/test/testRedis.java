package com.jt.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class testRedis {

    @Autowired
    private Jedis jedis;

    @Test
    public void testSetGet(){
        jedis.set("a","ccaa");
    }

    @Test
    public void replace(){
        System.out.println(jedis);
    }


    @Test
    public void testMulti(){
        Transaction transaction = jedis.multi();
        try {
            transaction.set("a", "aa");
            transaction.set("b", "bb");
        }catch (Exception e){
            transaction.discard();
        }
    }

    @Test
    public void testJedisCluster(){
        Set<HostAndPort> set = new HashSet();
        set.add(new HostAndPort("192.168.126.129", 7000));
        set.add(new HostAndPort("192.168.126.129", 7001));
        set.add(new HostAndPort("192.168.126.129", 7002));
        set.add(new HostAndPort("192.168.126.129", 7003));
        set.add(new HostAndPort("192.168.126.129", 7004));
        set.add(new HostAndPort("192.168.126.129", 7005));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(60);
        jedisPoolConfig.setMinIdle(20);
        JedisCluster jedisCluster = new JedisCluster(set,jedisPoolConfig);
        jedisCluster.set("myCluster", "我的Jedis集群");
        System.out.println(jedisCluster.get("myCluster"));
    }
}
