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

import com.warrior.model.Order;
import com.warrior.model.User;
import com.warrior.request.OrderRequest;
import com.warrior.service.OrderService;
import com.warrior.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
	private OrderService orderService;
	private UserService userService;

	@Autowired
	public AdminOrderController(OrderService orderService, UserService userService) {
		super();
		this.orderService = orderService;
		this.userService = userService;
	}



	
	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(
			@PathVariable Long id,
			@RequestParam (required = false) String orderStatus,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user=userService.findUserByJwtToken(jwt);
		List<Order> order = orderService.getRestaurantsOrder(id,orderStatus);
		return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	
	@PutMapping("/order/{id}/{orderStatus}")
	public ResponseEntity<Order> updateOrderSatus(
			@PathVariable Long id,
			@PathVariable String orderStatus,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user=userService.findUserByJwtToken(jwt);
		Order order = orderService.updateOrder(id, orderStatus);
		return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


}
