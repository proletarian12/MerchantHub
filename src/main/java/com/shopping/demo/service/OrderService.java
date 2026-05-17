package com.shopping.demo.service;

import com.shopping.demo.dao.OrderDao;
import com.shopping.demo.entity.Orders;
import com.shopping.demo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 创建订单（事务管理）
     */
    @Transactional
    public int createOrder(Orders order, OrderItem orderItem) {
        // 保存订单
        int orderId = orderDao.save(order);

        // 设置订单ID
        orderItem.setOrderId(orderId);

        // 保存订单项
        orderDao.saveOrderItem(orderItem);

        return orderId;
    }

    /**
     * 根据ID查询订单
     */
    public Orders findById(Integer orderId, Integer userId) {
        return orderDao.findById(orderId, userId);
    }

    /**
     * 更新订单状态
     */
    public void updateStatus(Integer orderId, Integer status) {
        orderDao.updateStatus(orderId, status);
    }
}