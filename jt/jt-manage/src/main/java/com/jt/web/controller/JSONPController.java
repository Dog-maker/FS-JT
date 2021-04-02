package com.jt.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JSONPController {
    /**
     * 完成跨域访问
     * url: http://manage.jt.com/web/testJSONP?callback=jQuery111105852031599507761_1616601602205&_=1616601602206
     * 参数:callback = jQuery111105852031599507761_1616601602205&_=1616601602206
     * 返回值 callback(jsonp)
     */

    public String Jsonp(String callback){
        Map map = new HashMap();
        map.put("id", "1");
        map.put("name", "abc");
        String jsonp = ObjectMapperUtil.toJson(map);
        callback = callback+"("+jsonp+")";
        return callback;
    }


    public JSONPObject Jsonp2(String callback){
        Map map = new HashMap();
        map.put("id", "1");
        map.put("name", "abc");

        return new JSONPObject(callback, map);
    }

}
