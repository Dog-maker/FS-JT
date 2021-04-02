package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data   //方法只会重写自己的属性，不会添加父类的属性
@Accessors(chain = true)    //链式加载规则,重写set方法，返回自己的对象(User)
@NoArgsConstructor  //无参构造
@AllArgsConstructor //全参构造
@TableName("user")      //实现表与对象的关联
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String sex;

}
