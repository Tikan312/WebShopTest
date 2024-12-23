package com.webstore.order_management.service;

import com.webstore.order_management.model.Order;
import com.webstore.order_management.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        Order order = orderService.getOrder(orderId);
        assertEquals(orderId, order.getId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testGetOrderById_NotFound() {
        Long orderId = 2L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.getOrder(orderId));
        assertEquals("Заказ не найден по ID: " + orderId, exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testCreateOrder_Success() {
        Order mockOrder = new Order();
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        Order savedOrder = orderService.createOrder(mockOrder);
        assertEquals(mockOrder, savedOrder);
        verify(orderRepository, times(1)).save(mockOrder);
    }

    @Test
    void testCreateOrder_AlreadyExists() {
        Order mockOrder = new Order();
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(mockOrder));
        assertEquals("Заказ уже существует", exception.getMessage());
        verify(orderRepository, times(1)).save(mockOrder);
    }

    @Test
    void testUpdateOrder_Success() {
        Order mockOrder = new Order();
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
    }

    @Test
    void testUpdateOrder_AlreadyExists() {
        Order mockOrder = new Order();
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
    }

    @Test
    void testDeleteOrder_Success() {
        Order mockOrder = new Order();
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        orderService.deleteOrder(mockOrder.getId());
        verify(orderRepository, times(1)).save(mockOrder);
    }

    @Test
    void testDeleteOrder_NotFound() {
        Order mockOrder = new Order();
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.deleteOrder(mockOrder.getId()));
    }
}