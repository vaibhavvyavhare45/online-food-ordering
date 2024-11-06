package com.warrior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warrior.model.IngredientsItem;

@Repository
public interface IngredientItemRepository  extends JpaRepository<IngredientsItem , Long>{
	List<IngredientsItem> findByRestaurentId(Long id);

}
