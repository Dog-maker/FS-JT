package com.jt.controller;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemCatController {

    @Autowired
    ItemCatService itemCatService;

    @RequestMapping("/item/cat/queryItemName")
    public String queryItemName(Long itemCatId){
        return itemCatService.findItemName(itemCatId);
    }

    @RequestMapping("/item/cat/list")
    public List<EasyUITree> queryCat(Long id){
        Long parentId = (id==null)?0:id;
        return  itemCatService.findTree(parentId);
        //return itemCatService.findCatchTree(parentId);
    }
}
