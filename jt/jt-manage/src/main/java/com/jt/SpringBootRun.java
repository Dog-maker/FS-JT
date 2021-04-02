package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@MapperScan("com.jt.mapper")
public class SpringBootRun {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootRun.class, args);
	}
}
