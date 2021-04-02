package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.cartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DubboCartServiceImpl implements DubboCatService{

    @Autowired
    cartMapper cartMapper;

    @Override
    public List<Cart> getCartList(Integer userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        return cartList;
    }

    @Override
    @Transactional
    public void updaeCartNum(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("item_id", cart.getItemId());
        //因为动态生成的sql会将不是null的值全部修改，但是我们只改num，所以item_id和user_id需要为null
        Cart newcart = new Cart();
        newcart.setNum(cart.getNum());
        cartMapper.update(newcart,queryWrapper);
    }

    @Override
    @Transactional
    public void addCart(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("item_id", cart.getItemId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);
        if(cartDB == null){
            //新增入库
            cartMapper.insert(cart);
        }else{
            //更新数量  MP方式
            int num = cart.getNum() + cartDB.getNum(); //数量求和
            cartDB.setNum(num);
            cartMapper.updateCartNum(cartDB);
        }
    }

    @Override
    @Transactional
    public void deleteCart(Cart cart) {
        QueryWrapper queryWrapper = new QueryWrapper<Cart>(cart);
        cartMapper.delete(queryWrapper);
    }

}
