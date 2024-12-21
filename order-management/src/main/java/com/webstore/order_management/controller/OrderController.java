package com.webstore.order_management.controller;

import com.webstore.order_management.model.Order;
import com.webstore.order_management.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOrderById(@PathVariable Long id) {
        try {
            return orderService.getOrder(id);
        } catch (RuntimeException e) {
            System.err.println("Заказ не найден по ID: " + id);
            throw new RuntimeException("Заказ не найден по ID: " + id);
        }
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody @Valid Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/orders/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        orderService.updateOrderStatus(id, status);
        return orderService.getOrder(id);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            //System.out.println("Заказ успешно удален!");
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            System.err.println("Такого заказа не существует по ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого заказа не существует по ID: " + id);
        }
    }
}
