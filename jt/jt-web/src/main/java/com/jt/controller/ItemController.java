package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.DubboItemService;
import com.jt.util.UserLocalThread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Reference(check = false)
    DubboItemService ItemService;

    /**
     * url :http://www.jt.com/items/562379.html
     */
    @RequestMapping("/items/{ItemId}")
    public String ItemPage(@PathVariable Long ItemId, Model model){
        Item item = ItemService.findItemById(ItemId);
        ItemDesc itemDesc = ItemService.findItemDescById(ItemId);

        //传参到   JSP页面
        model.addAttribute(item);
        model.addAttribute(itemDesc);

        return "item";
    }

}
