package com.warrior.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.model.Category;
import com.warrior.model.Restaurant;
import com.warrior.repository.CategoryRepository;
import com.warrior.service.CategoryService;
import com.warrior.service.RestaurantService;

@Service
public class CategoryServcieImpl implements CategoryService{

	private CategoryRepository categoryRepository;
	private RestaurantService restaurantService;
	
	@Autowired
	public CategoryServcieImpl(CategoryRepository categoryRepository, RestaurantService restaurantService) {
		super();
		this.categoryRepository = categoryRepository;
		this.restaurantService = restaurantService;
	}

	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
		Category category=new  Category();
		category.setName(name);
		category.setRestaurant(restaurant);;
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryRestaurantById(Long id) throws Exception {
		Restaurant restaurant=restaurantService.findRestaurantById(id);
		
		return categoryRepository.findByRestaurantId(id);
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
		Optional<Category> optionalCategory=categoryRepository.findById(id);
		if(optionalCategory.isEmpty()) {
			throw new Exception("category not found"+id);
			} 
		return optionalCategory.get();
	}

}
