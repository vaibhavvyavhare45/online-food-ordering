package com.warrior.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warrior.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
