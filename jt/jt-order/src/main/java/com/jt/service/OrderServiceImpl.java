package com.jt.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;


	@Override
	@Transactional
	public String saveOrder(Order order) {

		String orderId = ""+order.getOrderId()+System.currentTimeMillis();
		order.setOrderId(orderId).setStatus(1);

		orderMapper.insert(order);

		orderShippingMapper.insert(order.getOrderShipping().setOrderId(orderId));
		List<OrderItem> list = new ArrayList();
		for (OrderItem orderItem : order.getOrderItems()){
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}
		return orderId;
	}

	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping orderShipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("order_id",id);
		List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
		return order.setOrderShipping(orderShipping).setOrderItems(orderItems);
	}
}
