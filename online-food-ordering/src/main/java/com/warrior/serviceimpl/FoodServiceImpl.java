package com.warrior.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.model.Category;
import com.warrior.model.Food;
import com.warrior.model.Restaurant;
import com.warrior.repository.FoodRepository;
import com.warrior.request.CreateFooodRequest;
import com.warrior.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
	
	private  FoodRepository foodRepository;
	
	
	@Autowired
	public FoodServiceImpl(FoodRepository foodRepository) {
		super();
		this.foodRepository = foodRepository;
	}
	

	@Override
	public Food createFood(CreateFooodRequest req, Category category, Restaurant restaurant) {
		Food food=new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredientsItems(req.getIngredients());
		food.setSeasonal(req.isSeasional());
		food.setVegetarian(req.isVegetarin());
		Food saveFood=foodRepository.save(food);
		restaurant.getFoods().add(saveFood);
		return saveFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
	Food food=findFoodById(foodId);
	food.setRestaurant(null);
	foodRepository.save(food);
		
	}

	@Override
	public List<Food> getRestaurantFood(Long restaurantId, boolean isVegitarain, boolean isNonVeg, boolean isSeasional,
			String foodCategory) {
	List<Food> foods=foodRepository.findByRestaurantId(restaurantId);
	if(isVegitarain) {
		foods=filterByVegitarain(foods,isVegitarain);
	}
	if(isNonVeg) {
		foods=filterByNonVeg(foods,isNonVeg);
	}
	if(isSeasional) {
		foods=filterBySeasonal(foods,isSeasional);
	}
	if(foodCategory!=null && !foodCategory.equals("")) {
		foods=filterByCategory(foods,foodCategory);
		
	}
	
	
		return foods;
	}

	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		
		return foods.stream().filter(food -> {
			if(food.getFoodCategory()!=null) {
				return food.getFoodCategory().getName().equals(foodCategory);
			}
			return false;
		}).collect(Collectors.toList());
	}


	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasional) {
		
		return foods.stream().filter(food -> food.isSeasonal()==isSeasional).collect(Collectors.toList());
	}


	private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
		
		return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
	}


	private List<Food> filterByVegitarain(List<Food> foods, boolean isVegitarain) {
		
		return foods.stream().filter(food -> food.isVegetarian()==isVegitarain).collect(Collectors.toList());
	}


	@Override
	public List<Food> searchFood(String keyword) {
		
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Optional<Food> optionalFood=foodRepository.findById(foodId);
		if(optionalFood.isEmpty()) {
			throw new Exception("Food not exist!");
		}
		return optionalFood.get();
	}

	@Override
	public Food updateAvailibiltyStatus(Long foodId) throws Exception {
		Food food=findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		
		return foodRepository.save(food);
	}
	

}
