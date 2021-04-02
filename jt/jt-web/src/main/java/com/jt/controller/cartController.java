package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCatService;
import com.jt.util.CookieUtil;
import com.jt.util.GetUserUtil;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class cartController {

    @Autowired
    JedisCluster jedisCluster;

    @Reference
    DubboCatService CatService;

    /**
     * 购物车回显
     * url: http://www.jt.com/cart/show.html
     * 返回值 :cart.jsp
     * 获取用户
     * 获取信息  :carList
     */
    @RequestMapping("/show")
    public String show(HttpServletRequest request, Model model){
        //2.获取指定用户id的购物车信息
        List<Cart> cartList = CatService.getCartList(GetUserUtil.getUserId(request, jedisCluster));
        //向页面返回指定用户的用户车
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    /**
     * 用户购买数量修改
     * URL: http://www.jt.com/cart/update/num/562379/6
     * 参数   itemId(若存在多个restful参数，且参数和对象属性相同，可用对象接收)
     */
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public void updateVal(HttpServletRequest request,Cart cart){

        User user = GetUserUtil.getUser(request, jedisCluster);
        cart.setUserId(Integer.toUnsignedLong(user.getId()));
        CatService.updaeCartNum(cart);
    }

    /**
     * 从新回到购物车界面(从商品查看页面)
     * url: http://www.jt.com/cart/add/562379.html
     * 返回值  重定向到购物车界面
     */
    @RequestMapping("/add/{itemId}")
    public String addToCart(HttpServletRequest request,Cart cart){
        cart.setUserId(Long.valueOf(GetUserUtil.getUserId(request,jedisCluster)));
        CatService.addCart(cart);
        return "redirect:/cart/show";
    }

    /**
     * URL: http://www.jt.com/cart/delete/562379.html
     * 返回值  ：购物车页面
     * @param itemId
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId,HttpServletRequest request){
        //获取用户ID
        Cart cart = new Cart();
        cart.setItemId(itemId);
        cart.setUserId(Long.valueOf(GetUserUtil.getUserId(request,jedisCluster)));
        //删除指定购物车
        CatService.deleteCart(cart);
        return "redirect:/cart/show";
    }
}
