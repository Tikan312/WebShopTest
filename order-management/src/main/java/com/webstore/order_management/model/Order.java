package com.webstore.order_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
//Создаем заказ как сущность для БД
@Entity
@Table(name = "orders")
public class Order {
    //Даем полю тег ID и позволяем БД самой дать ID объекту
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Указываем на то, чтобы время создания заказа не могло быть пустым и было необновляемым
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    //Создаем поле времени апдейта по заказу
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    //Создаем связь Мэни ту мэни, говооря БД, что поле может быть привязано к нескольким таблицам и создаем промежуточную таблицу
    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @NotNull(message = "Список продуктов не может быть пустым")
    @Size(min = 1, message = "Должен быть как минимум один продукт")
    private List<Product> products;
    //Преобразуем енам в стрингу для БД
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Статус заказа обязателен")
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id != null && id.equals(order.id);
    }

    @Override
    public int hashCode() {
        if (id != null) return id.hashCode();
        else return 0;
    }
}
