package com.warrior.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.model.Address;
import com.warrior.model.Cart;
import com.warrior.model.CartItem;
import com.warrior.model.Order;
import com.warrior.model.OrderItem;
import com.warrior.model.Restaurant;
import com.warrior.model.User;
import com.warrior.repository.AddressRepository;
import com.warrior.repository.OrderItemRepository;
import com.warrior.repository.OrderRepository;
import com.warrior.repository.RestaurantRepository;
import com.warrior.repository.UserRepository;
import com.warrior.request.OrderRequest;
import com.warrior.service.CartService;
import com.warrior.service.OrderService;
import com.warrior.service.RestaurantService;

@Service
public class OrderServiceImpl implements OrderService {
	private OrderRepository orderRepository;
	private OrderItemRepository orderItemRepository;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private RestaurantService restaurantService;
	private CartService cartService;
	
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			AddressRepository addressRepository,UserRepository userRepository,
			RestaurantService restaurantService,CartService cartService) {
		super();
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.addressRepository=addressRepository;
		this.userRepository=userRepository;
		this.restaurantService=restaurantService;
		this. cartService=cartService;
	}

	@Override
	public Order creatOrder(OrderRequest order, User user) throws Exception {
		Address shippingAddress=order.getDeliveryAddress();
		Address saveAddress=addressRepository.save(shippingAddress);
		if(!user.getAddresses().contains(saveAddress)) {
			user.getAddresses().add(saveAddress);
			userRepository.save(user);
		}
		Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());
		Order createOrder=new Order();
		createOrder.setCustomer(user);
		createOrder.setCreatedAt(new Date());
		createOrder.setOrderStatus("PENDING");
		createOrder.setDeliveryAddress(saveAddress);
		createOrder.setRestaurant(restaurant);
	
		Cart cart=cartService.findCartByUserId(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		for(CartItem cartItem:cart.getItem()) {
			OrderItem orderItem=new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			
			OrderItem saveOrderItem=orderItemRepository.save(orderItem);
			orderItems.add(saveOrderItem);
		}
		Long totalPrice=cartService.calculateCartTotals(cart);
		createOrder.setItems(orderItems);
		createOrder.setTotalPrice(totalPrice);
		
		Order saveOrder=orderRepository.save(createOrder);
		restaurant.getOrder().add(saveOrder);
		
		return createOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		Order order=findOrderById(orderId);
		if(orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") 
				|| orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
			order.setOrderStatus(orderStatus);
			return orderRepository.save(order);
		}
		throw new Exception("Please select valid order status");
		
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		Order order=findOrderById(orderId);
		orderRepository.deleteById(orderId);
		
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {	
		
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
		
	List<Order>orders=orderRepository.findByRestaurantId(restaurantId);
	if(orderStatus!=null) {
		orders=orders.stream().filter(order ->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
	}
	return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		Optional<Order> optinalOrder=orderRepository.findById(orderId);
		if(optinalOrder.isEmpty()) {
			throw new Exception("order not  found");
		}
		return optinalOrder.get();
	}

}
