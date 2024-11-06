package com.warrior.request;

import java.util.List;

import com.warrior.model.Category;
import com.warrior.model.IngredientsItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CreateFooodRequest {
	private String name;
	private String description;
	private Long price;
	private Category category;
	private List<String> images;
	
	private Long restaurantId;
	
	private boolean vegetarin;
	private boolean seasional;
	private List<IngredientsItem> ingredients;

}
