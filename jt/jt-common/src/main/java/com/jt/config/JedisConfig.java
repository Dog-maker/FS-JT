package com.jt.config;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class JedisConfig {

//    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private Integer port;

    //redis分页
//    @Value("${redis.host}")
//    private String host;
//
//    @Value("${redis.port1}")
//    private int port1;
//
//    @Value("${redis.port2}")
//    private int port2;
//
//    @Value("${redis.port3}")
//    private int port3;

    //redis哨兵       集群节点
    @Value("${redis.node}")
    private String node;

    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> set = new HashSet<>();
        String[] ArrayNodes = node.split(",");
        for(String Node : ArrayNodes){
            String[] node = Node.split(":");
            set.add(new HostAndPort(node[0],Integer.parseInt(node[1])));
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(60);
        jedisPoolConfig.setMinIdle(40);
        JedisCluster jedisCluster = new JedisCluster(set,jedisPoolConfig);
        return jedisCluster;
    }


//    @Bean
//    public JedisSentinelPool jedisSentinelPool(){
//        Set<String> set = new HashSet();
//        set.add(node);
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(1000);
//        jedisPoolConfig.setMinIdle(40);
//        jedisPoolConfig.setMaxIdle(60);
//        return new JedisSentinelPool("mymaster", set,jedisPoolConfig);
//    }



//    /**
//     * 测试哨兵
//     */
//    public void testSentienl(){
//
//        Set<String> set = new HashSet();
//        set.add("192.168.126.129:26379");
//        //哨兵    redis池
//        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster",set);
//
//    }

    /**
     * ShardsJedis测试    3个redis服务
     * @return ShardedJedis
     */
//    @Bean
//    public ShardedJedis shardedJedis(){
//        List<JedisShardInfo> jedisShardInfos = new ArrayList<>();
//        jedisShardInfos.add(new JedisShardInfo(host,port2));
//        jedisShardInfos.add(new JedisShardInfo(host,port1));
//        jedisShardInfos.add(new JedisShardInfo(host,port3));
//        ShardedJedis shardedJedis = new ShardedJedis(jedisShardInfos);
//        return shardedJedis;
//    }

//    @Bean
//    public Jedis jedis(){
//        return new Jedis(host,port);
//    }
}
