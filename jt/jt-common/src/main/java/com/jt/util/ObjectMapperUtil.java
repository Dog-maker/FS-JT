package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

    public static final ObjectMapper Mapper = new ObjectMapper();

    public static String toJson(Object target){
        try {
            return Mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
//            将检查异常转化为运行时异常
            throw new RuntimeException(e);
        }
    }

    public static <T>T toObject(String json,Class<T> targetClass){
        try {
            return Mapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
