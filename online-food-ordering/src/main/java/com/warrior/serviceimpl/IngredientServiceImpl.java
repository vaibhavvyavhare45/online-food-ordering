package com.warrior.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.model.IngredientCategory;
import com.warrior.model.IngredientsItem;
import com.warrior.model.Restaurant;
import com.warrior.repository.IngredientCategoryRepository;
import com.warrior.repository.IngredientItemRepository;
import com.warrior.service.IngredientService;
import com.warrior.service.RestaurantService;

@Service
public class IngredientServiceImpl implements IngredientService {

	private IngredientItemRepository ingredientItemRepository;
	private IngredientCategoryRepository ingredientCategoryRepository;
	private RestaurantService restaurantService;

	@Autowired
	public IngredientServiceImpl(IngredientItemRepository ingredientItemRepository,
			IngredientCategoryRepository ingredientCategoryRepository, RestaurantService restaurantService) {
		super();
		this.ingredientItemRepository = ingredientItemRepository;
		this.ingredientCategoryRepository = ingredientCategoryRepository;
		this.restaurantService = restaurantService;
	}

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		IngredientCategory category = new IngredientCategory();
		category.setRestaurant(restaurant);;
		category.setName(name);
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategory(Long id) throws Exception {
		Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
		if (opt.isEmpty()) {
			throw new Exception("ingredient category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
		restaurantService.findRestaurantById(id);
		
		return ingredientCategoryRepository.findByRestaurantId(id);
	}
	
	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		IngredientCategory category=findIngredientCategory(categoryId);
		IngredientsItem ingredientsItem=new IngredientsItem();
		ingredientsItem.setName(ingredientName);
		ingredientsItem.setRestaurent(restaurant);
		ingredientsItem.setCategory(category);
		
		IngredientsItem ingredients=ingredientItemRepository.save(ingredientsItem);
		category.getIngredientsItems().add(ingredients);
		return ingredients;
	}

	@Override
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
		
		return ingredientItemRepository.findByRestaurentId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		Optional<IngredientsItem> optionalIngredientItem=ingredientItemRepository.findById(id);
		if(optionalIngredientItem.isEmpty()) {
			throw new Exception("ingredient not found");
		}
		IngredientsItem ingredientsItem=optionalIngredientItem.get();
		ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
		return ingredientItemRepository.save(ingredientsItem);
	}

}
