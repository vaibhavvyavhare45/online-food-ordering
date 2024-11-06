package com.warrior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.model.Restaurant;
import com.warrior.model.User;
import com.warrior.request.CreateRestaurantRequest;
import com.warrior.response.MessageResponse;
import com.warrior.service.RestaurantService;
import com.warrior.service.UserService;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
	private RestaurantService restaurantService;
	private UserService userService;
	

	@Autowired
	public AdminRestaurantController(RestaurantService restaurantService, UserService userService) {
		super();
		this.restaurantService = restaurantService;
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.createRestaurant(req, user);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt, @PathVariable Long id

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.updateRestaurant(id, req);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteRestaurant(
			@RequestHeader("Authorization") String jwt, @PathVariable Long id

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		restaurantService.deleteRestaurant(id);
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setMessage("restaurant deleted successfully");
		return new ResponseEntity<>(messageResponse, HttpStatus.OK);

	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant> updateRestaurantStatus(
			@RequestHeader("Authorization") String jwt, @PathVariable Long id

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

		return new ResponseEntity<>(restaurant, HttpStatus.OK);

	}

	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantByUserId(
			@RequestHeader("Authorization") String jwt

	) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

		return new ResponseEntity<>(restaurant, HttpStatus.OK);

	}

}
