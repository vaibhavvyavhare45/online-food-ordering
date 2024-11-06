package com.warrior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warrior.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public List<Category> findByRestaurantId(Long id);

}
