package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDemo2ApplicationTests {

    @Value("${redis.test}")
    private String test;

    @Test
    void contextLoads() {
        System.out.println(test);
    }

}
