package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemCatMapper extends BaseMapper<ItemCat> {
    @Select("SELECT * FROM tb_item_cat WHERE parent_id = #{parentId}")
    List<ItemCat> findItemTree(Long parentId);
}
