package com.webstore.order_management.service;

import com.webstore.order_management.model.Order;
import com.webstore.order_management.model.OrderStatus;
import com.webstore.order_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.NEW);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long orderId) {
        try {
        return orderRepository.findById(orderId).orElseThrow();
        } catch (Exception e) {
            System.err.println("Заказ не найден по ID: " + orderId);
            throw new RuntimeException("Заказ не найден по ID: " + orderId);
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = getOrder(orderId);
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
