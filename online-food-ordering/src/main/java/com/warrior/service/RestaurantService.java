package com.warrior.service;

import java.util.List;

import com.warrior.dto.RestaurentDto;
import com.warrior.model.Restaurant;
import com.warrior.model.User;
import com.warrior.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedRestaurant)throws Exception;
	public void deleteRestaurant(Long restaurantId) throws Exception;
	public List<Restaurant> getAllRestaurant();
	public List<Restaurant> searchRestaurant(String keyword);
	public Restaurant findRestaurantById(Long id) throws Exception;
	public Restaurant getRestaurantByUserId(Long userId)throws Exception;
	public RestaurentDto addToFavorites(Long restaurantId,User user)throws Exception;
	public Restaurant updateRestaurantStatus(Long id)throws Exception;
	
}
