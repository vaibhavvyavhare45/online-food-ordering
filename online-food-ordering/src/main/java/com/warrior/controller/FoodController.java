package com.warrior.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.model.Food;
import com.warrior.model.Restaurant;
import com.warrior.model.User;
import com.warrior.request.CreateFooodRequest;
import com.warrior.service.FoodService;
import com.warrior.service.RestaurantService;
import com.warrior.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	private FoodService foodService;

	private UserService userService;

	private RestaurantService restaurantService;

	
	@Autowired           
	public FoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
		super();
		this.foodService = foodService;
		this.userService = userService;
		this.restaurantService = restaurantService;
	}



	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Food> food = foodService.searchFood(name);

		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian,
			@RequestParam boolean seasonal,
			@RequestParam boolean nonVeg,
			@RequestParam(required = false) String foodCategory,
			@PathVariable Long restaurantId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Food> food = foodService.getRestaurantFood(restaurantId, seasonal, vegetarian, nonVeg, foodCategory);
		
		return new ResponseEntity<>(food, HttpStatus.OK);
	}

}
