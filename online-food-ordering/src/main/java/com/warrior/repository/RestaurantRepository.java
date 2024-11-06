package com.warrior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.warrior.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	 @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
	           "OR LOWER(r.cuisineType) LIKE LOWER(CONCAT('%', :query, '%'))")
	    List<Restaurant> findBySearchQuery(@Param("query") String query);

	    Restaurant findByOwnerId(Long userId);

}
