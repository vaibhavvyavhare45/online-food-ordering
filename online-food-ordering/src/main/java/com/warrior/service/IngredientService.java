package com.warrior.service;

import java.util.List;

import com.warrior.model.IngredientCategory;
import com.warrior.model.IngredientsItem;

public interface IngredientService {
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

	public IngredientCategory findIngredientCategory(Long id) throws Exception;

	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception;

	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

	public IngredientsItem updateStock(Long id) throws Exception;

}
