package com.warrior.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.dto.RestaurentDto;
import com.warrior.model.Address;
import com.warrior.model.Restaurant;
import com.warrior.model.User;
import com.warrior.repository.AddressRepository;
import com.warrior.repository.RestaurantRepository;
import com.warrior.repository.UserRepository;
import com.warrior.request.CreateRestaurantRequest;
import com.warrior.service.RestaurantService;


@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	private RestaurantRepository restaurantRepository;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	
	@Autowired
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository, AddressRepository addressRepository,
			UserRepository userRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address address= addressRepository.save(req.getAddress());
		Restaurant restaurant=new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		
		
		
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		if(restaurant.getCuisineType()!=null) {
			restaurant.setCuisineType(updatedRestaurant.getCuisineType());
		}
		if(restaurant.getDescription()!=null) {
			restaurant.setDescription(updatedRestaurant.getDescription());
		}
		if(restaurant.getName()!=null) {
			restaurant.setName(updatedRestaurant.getDescription());
		}
		return restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		restaurantRepository.delete(restaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		
		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		
		return restaurantRepository.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		Optional<Restaurant> opt=restaurantRepository.findById(id);
		if(opt.isEmpty()) {
			throw new Exception("restaurant not found with id"+id);
		}
		
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
		if(restaurant==null) {
			throw new Exception("restaurant not found with owner id"+userId);
		}
		return restaurant;
	}

	@Override
	public RestaurentDto addToFavorites(Long restaurantId, User user) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		RestaurentDto dto=new RestaurentDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);
		/*
		 * if(user.getFavorites().contains(dto)) { user.getFavorites().remove(dto); }
		 * else { user.getFavorites().add(dto); userRepository.save(user); }
		 */
		boolean isFavorited=false;
		List<RestaurentDto> favorites=user.getFavorites();
		for(RestaurentDto restaurentDto:favorites) {
			if(restaurentDto.getId().equals(restaurantId)) {
				isFavorited=true;
				break;
			}
		}
		if(isFavorited) {
			favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
		}else {
			favorites.add(dto);
		}
		userRepository.save(user);
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		Restaurant restaurant=findRestaurantById(id);
		restaurant.setOpen(!restaurant.isOpen());
		return restaurantRepository.save(restaurant);
	}

}
