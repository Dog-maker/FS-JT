package com.jt;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class TestClient {

    @Test
    public void testHttpClient(){
        HttpClient httpClient = HttpClients.createDefault();
        String url = "http://www.jt.com/testJS.html";
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse execute = httpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = execute.getEntity();
                String result = EntityUtils.toString(entity,"utf-8");
                System.out.println(result);
            }else {
                System.out.println("响应失误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test(){
        //(h = key.hashCode()) ^ (h >>> 16)
        int x = 17;
        System.out.println(x^(16>>>16));;
    }
}
