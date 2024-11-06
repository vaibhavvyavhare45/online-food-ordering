package com.warrior.service;

import java.util.List;

import com.warrior.model.Category;
import com.warrior.model.Food;
import com.warrior.model.Restaurant;
import com.warrior.request.CreateFooodRequest;

public interface FoodService {

	public Food createFood(CreateFooodRequest req, Category category,Restaurant restaurant);
	
	void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurantFood(Long restaurantId,boolean isVegitarain,boolean isNonVeg,boolean isSeasional,String foodCategory);
	public List<Food> searchFood(String keyword);
	public Food  findFoodById(Long foodId) throws Exception;
	public Food updateAvailibiltyStatus(Long foodId)throws Exception;
	
}
