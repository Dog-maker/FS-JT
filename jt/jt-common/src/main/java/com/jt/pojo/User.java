package com.jt.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_user")
@Data
@Accessors(chain = true)
public class User extends BasePojo{

    public static String TICKET_NAME = "JT_TICKET";

    /**
     * idbigint(20)
     * usernamevarchar(50)
     * passwordvarchar(32)MD5加密
     * phonevarchar(20)
     * emailvarchar(50)
     * createddatetime
     * updateddatetime
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
}
