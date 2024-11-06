package com.warrior.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrior.config.JwtProvider;
import com.warrior.model.Cart;
import com.warrior.model.USER_ROLE;
import com.warrior.model.User;
import com.warrior.repository.CartRepository;
import com.warrior.repository.UserRepository;
import com.warrior.request.LoginRequest;
import com.warrior.response.AuthResponse;
import com.warrior.service.CustomerUserDetailsService;
import com.warrior.serviceimpl.CustomerUserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtProvider jwtProvider;
	private CustomerUserDetailsServiceImpl customerUserDetailsServiceImpl;
	private CartRepository cartRepository;
	@Autowired
	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
			CustomerUserDetailsServiceImpl customerUserDetailsServiceImpl, CartRepository cartRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.customerUserDetailsServiceImpl = customerUserDetailsServiceImpl;
		this.cartRepository = cartRepository;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
		User isEmailExist=userRepository.findByEmail(user.getEmail());
		if(isEmailExist!=null) {
			throw new Exception("Email is alrady used with another account");
		}
		User createdUser=new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User saveUser=userRepository.save(createdUser);
		
		Cart cart=new Cart();
		cart.setCustomer(saveUser);
		cartRepository.save(cart);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("register success");
		authResponse.setRole(saveUser.getRole());
		return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
		String userName=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		Authentication authentication=authenticate(userName,password);
		
		Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
		String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		String jwt=jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("login success");
		authResponse.setRole(USER_ROLE.valueOf(role));
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
		
		
	}

	
	private Authentication authenticate(String userName, String password) {
		UserDetails userDetails=customerUserDetailsServiceImpl.loadUserByUsername(userName);
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username!");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password!");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	}
	

}
