package com.warrior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.model.Food;
import com.warrior.model.Restaurant;
import com.warrior.model.User;
import com.warrior.request.CreateFooodRequest;
import com.warrior.response.MessageResponse;
import com.warrior.service.FoodService;
import com.warrior.service.RestaurantService;
import com.warrior.service.UserService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
	
	private FoodService foodService;
	
	private UserService userService;
	
	private RestaurantService restaurantService;
	
	@Autowired
	public AdminFoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
		super();
		this.foodService = foodService;
		this.userService = userService;
		this.restaurantService = restaurantService;
	}
	
	@PostMapping
	public ResponseEntity<Food> createFood(@RequestBody CreateFooodRequest req,
			@RequestHeader("Authorization") String jwt	
			) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
		Food food=foodService.createFood(req, req.getCategory(), restaurant);
		
		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
			@RequestHeader("Authorization") String jwt	
			) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		foodService.deleteFood(id);
		MessageResponse messageResponse=new MessageResponse();
		messageResponse.setMessage("food deleted successfully");
		
		return new ResponseEntity<>(messageResponse,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateFoodAvailibilityStatus(@PathVariable Long id,
			@RequestHeader("Authorization") String jwt	
			) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Food food=foodService.updateAvailibiltyStatus(id);
		
		
		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}
	
	

}
