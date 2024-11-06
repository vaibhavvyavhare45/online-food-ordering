package com.warrior.service;

import java.util.List;

import com.warrior.model.Category;

public interface CategoryService {
	public Category createCategory(String name, Long userId)throws Exception;
	
	public List<Category> findCategoryRestaurantById(Long restaurantId) throws Exception;
	
	public Category findCategoryById(Long id) throws Exception;

}
