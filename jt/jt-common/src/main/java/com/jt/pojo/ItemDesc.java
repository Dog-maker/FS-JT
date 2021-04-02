package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_desc")
@Data //toString方法只显示自己的属性,父级的不管.
@Accessors(chain = true)
public class ItemDesc extends BasePojo {

    @TableId
    private Long itemId; //与商品表的ID一致.
    private String itemDesc;

}
