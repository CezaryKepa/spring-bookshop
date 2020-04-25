package com.kepa.springlibraryapp.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAll();
    List<Order> findAllByStatus(OrderStatus orderStatus);
}