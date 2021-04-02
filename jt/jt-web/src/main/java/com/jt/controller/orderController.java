package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCatService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserLocalThread;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class orderController {

    @Reference(check = false)
    DubboCatService dubboCatService;

    @Reference(check = false)
    DubboOrderService OrderService;

    /**
     * 付款页面呈现
     * URL:http://www.jt.com/order/create.html
     */
    @RequestMapping("/create")
    public String create(Model model){
        //订单回显
        List<Cart> cartList = dubboCatService.getCartList(UserLocalThread.getUser().getId());
        model.addAttribute("carts",cartList);
        return "order-cart";
    }

    /**
     * //订单提交
     * URL: http://www.jt.com/order/submit
     * 返回页面：orderId
     */
    @ResponseBody
    @RequestMapping("/submit")
    public SysResult submit(Order order){
        order.setOrderId(String.valueOf(UserLocalThread.getUser().getId()));

        String orderId = OrderService.saveOrder(order);
        if (!StringUtils.hasLength(orderId))
            return SysResult.fail();
        return SysResult.success(orderId);
    }

    /**
     * 实现订单的查询,返回order对象
     * url: http://www.jt.com/order/success.html?id=191600674802663
     * 参数: id 订单的编号
     * 返回值: success
     * 页面取值: ${order.orderId}
     */
    @RequestMapping("/success")
    public String findOrderById(String id,Model model){

        Order order = OrderService.findOrderById(id);
        model.addAttribute("order",order);
        return "success";
    }

    /**
     * http://www.jt.com/order/myOrder.html
     */
    @RequestMapping("/myOrder")
    public String myOrder(){

        return "my-orders";
    }
}
