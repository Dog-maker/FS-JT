package com.jt.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestObjectMapper {

    @Test
    public void testMapper(){
//        ItemDesc itemDesc = new ItemDesc();
//        itemDesc.setItemId(500L).setItemDesc("测试objectMapper").setCreated(new Date()).setUpdated(itemDesc.getUpdated());
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String json = objectMapper.writeValueAsString(itemDesc);
//            System.out.println(json);
//            ItemDesc itemDesc1 = objectMapper.readValue(json, ItemDesc.class);
//            System.out.println(itemDesc1);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        String str = "hello";
        String str1 = "he"+new String("llo");
        System.out.println(str1);
        System.out.println(str == str1);

    }
}
