package com.warrior.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.model.Cart;
import com.warrior.model.CartItem;
import com.warrior.model.Food;
import com.warrior.model.User;
import com.warrior.repository.CartItemRepository;
import com.warrior.repository.CartRepository;
import com.warrior.request.AddCartItemRequest;
import com.warrior.service.CartService;
import com.warrior.service.FoodService;
import com.warrior.service.UserService;

@Service
public class CartServiceImpl implements CartService {

	private CartRepository cartRepository;
	private UserService userService;
	private CartItemRepository cartItemRepository;
	private FoodService foodService;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, UserService userService,
			CartItemRepository cartItemRepository, FoodService foodService) {
		super();
		this.cartRepository = cartRepository;
		this.userService = userService;
		this.cartItemRepository = cartItemRepository;
		this.foodService = foodService;
	}

	@Override
	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Food food = foodService.findFoodById(req.getFoodId());
		Cart cart = cartRepository.findByCustomerId(user.getId());
		for(CartItem cartItem:cart.getItem()) {
			if(cartItem.getFood().equals(food)) {
				int newQuantity=cartItem.getQuantity()+req.getQuntity();
				return updatCartItemQuantity(cartItem.getId(),newQuantity);
			}
		}
		CartItem newCartItem=new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuntity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuntity()*food.getPrice());
		CartItem saveCartItem=cartItemRepository.save(newCartItem);
		cart.getItem().add(saveCartItem);

		return saveCartItem;
	}

	@Override
	public CartItem updatCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
		if(cartItem.isEmpty()) {
			throw new Exception("cart item not found");
		}
		CartItem item=cartItem.get();
		item.setQuantity(quantity);
		// 5*100=500
		item.setTotalPrice(item.getFood().getPrice()*quantity);
		
		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user=userService.findUserByJwtToken(jwt);
		Cart cart=cartRepository.findByCustomerId(user.getId());
		Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
		if(cartItem.isEmpty()) {
			throw new Exception("cart item not found");
		}
		CartItem cartItem2=cartItem.get();
		cart.getItem().remove(cartItem2);
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total=0L;
		for(CartItem cartItem:cart.getItem()) {
			total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optionalCart=cartRepository.findById(id);
		if(optionalCart.isEmpty()) {
			throw new Exception("Cart not found with id"+id);
		}
		
		return optionalCart.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		//User user=userService.findUserByJwtToken(jwt);
	Cart cart= cartRepository.findByCustomerId(userId);
	cart.setTotal(calculateCartTotals(cart));
	return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		//User user=userService.findUserByJwtToken(jwt);
		Cart cart=findCartByUserId(userId);
		cart.getItem().clear();
		return cartRepository.save(cart);
	}

}
