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
import org.springframework.web.bind.annotation.RestController;

import com.warrior.model.Category;
import com.warrior.model.User;
import com.warrior.service.CategoryService;
import com.warrior.service.UserService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	private CategoryService categoryService;
	private UserService userService;

	@Autowired
	public CategoryController(CategoryService categoryService, UserService userService) {
		super();
		this.categoryService = categoryService;
		this.userService = userService;
	}
	@PostMapping("/admin/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Category createCategory = categoryService.createCategory(category.getName(), user.getId());
		return new ResponseEntity<>(createCategory, HttpStatus.CREATED);

	}
	@GetMapping("/category/restaurant/{id}")
	public ResponseEntity<List<Category>> getRestaurantCategory(
			@PathVariable Long id,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Category> categories = categoryService.findCategoryRestaurantById(id);
		return new ResponseEntity<>(categories, HttpStatus.OK);
		
	}
}