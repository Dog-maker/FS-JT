package com.jt.web.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.DubboItemService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DubboItemServiceImpl implements DubboItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public Item findItemById(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        return item;
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        ItemDesc itemDesc = itemDescMapper.selectById(itemId);
        return itemDesc;
    }
}
