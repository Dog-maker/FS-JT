package com.jt.service;

import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {
    public String findItemName(Long itemCatId);

    List<EasyUITree> findTree(Long parentId);

    List<EasyUITree> findCatchTree(Long parentId);
}
