package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CROSConfig implements WebMvcConfigurer {

    /**
     *  addMapping:允许什么样的请求访问:"/web/**"
     *  allowedOrigins允许访问的网址:"www.jt.com"
     *  allowedMethods允许什么类型的   ：get/post
     *  allowCredentials    cooike携带
     *
     *
     * @param registry
     */
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(false);
    }
}
