package com.warrior.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.dto.RestaurentDto;
import com.warrior.model.Restaurant;
import com.warrior.model.User;
import com.warrior.request.CreateRestaurantRequest;
import com.warrior.service.RestaurantService;
import com.warrior.service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	private RestaurantService restaurantService;
	private UserService userService;

	@Autowired
	public RestaurantController(RestaurantService restaurantService, UserService userService) {
		super();
		this.restaurantService = restaurantService;
		this.userService = userService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(
			@RequestHeader("Authorization") String jwt,
			@RequestParam String keyword

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);

	}
	
	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurant(
			@RequestHeader("Authorization") String jwt
			

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant = restaurantService.getAllRestaurant();
		return new ResponseEntity<>(restaurant, HttpStatus.OK);

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.findRestaurantById(id);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);

	}

	@PutMapping("/{id}/add-favorites")
	public ResponseEntity<RestaurentDto> addToFavorites(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		
		RestaurentDto dto=restaurantService.addToFavorites(id, user);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	

	

}
