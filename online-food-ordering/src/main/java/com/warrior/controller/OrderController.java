package com.warrior.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.model.Order;
import com.warrior.model.User;
import com.warrior.request.OrderRequest;
import com.warrior.service.OrderService;
import com.warrior.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {
	private OrderService orderService;
	private UserService userService;

	@Autowired
	public OrderController(OrderService orderService, UserService userService) {
		super();
		this.orderService = orderService;
		this.userService = userService;
	}

	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Order order = orderService.creatOrder(req, user);
		return new ResponseEntity<>(order, HttpStatus.OK);

	}

	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Order> order = orderService.getUsersOrder(user.getId());
		return new ResponseEntity<>(order, HttpStatus.OK);

	}

}
