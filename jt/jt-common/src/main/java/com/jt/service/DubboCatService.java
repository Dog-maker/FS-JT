package com.jt.service;

import com.jt.pojo.Cart;

import java.util.List;

public interface DubboCatService {
    List<Cart> getCartList(Integer userId);

    void updaeCartNum(Cart cart);

    void addCart(Cart cart);

    void deleteCart(Cart cart);
}
