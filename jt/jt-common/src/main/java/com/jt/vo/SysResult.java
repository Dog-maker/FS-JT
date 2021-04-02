package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysResult {
    private Integer status;
    private String msg;
    private Object data;



    //封装一些静态API 简化用户调用过程.
    public static SysResult fail(){

        return new SysResult(201,"服务器调用失败",null);
    }

    public static SysResult success(){

        return new SysResult(200, "业务执行成功!!!", null);
    }

    public static SysResult success(Object data){

        return new SysResult(200, "业务执行成功!!!", data);
    }
}
