package com.warrior.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warrior.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	public Cart findByCustomerId(Long userId);

}
