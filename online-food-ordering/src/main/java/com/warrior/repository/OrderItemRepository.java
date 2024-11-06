package com.warrior.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warrior.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
