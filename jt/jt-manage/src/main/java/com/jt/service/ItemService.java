package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {


    EasyUITable findObjectByPage(int page, int rows);

    void saveItem(Item item, ItemDesc itemDesc);

    void updateItem(Item item, ItemDesc itemDesc);

    void deleteItemByIds(Long[] ids);

    void updateStatus(Integer status, Long[] ids);

    ItemDesc findItemDescById(Long itemId);
}
