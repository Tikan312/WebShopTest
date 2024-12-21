package com.webstore.order_management.service;

import com.webstore.order_management.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);
    Order getOrder(Long orderId);
    List<Order> getAllOrders();
    void updateOrderStatus(Long orderId, String status);
    void deleteOrder(Long orderId);
}
