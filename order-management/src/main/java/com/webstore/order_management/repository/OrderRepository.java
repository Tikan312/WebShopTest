package com.webstore.order_management.repository;

import com.webstore.order_management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Наследуем JPArepository с ссылкой на сущность и на первый ключ
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
