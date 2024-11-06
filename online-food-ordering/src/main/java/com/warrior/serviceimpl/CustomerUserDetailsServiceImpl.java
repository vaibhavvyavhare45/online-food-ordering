package com.warrior.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.warrior.model.USER_ROLE;
import com.warrior.model.User;
import com.warrior.repository.UserRepository;
import com.warrior.service.CustomerUserDetailsService;


/**
 * @author Vaibhav
 * this service to stop the spring security stop the auto generating password
 * but if I remove @Service annotation that time spring security generate the password
 * 
 *
 */
@Service
public class CustomerUserDetailsServiceImpl implements CustomerUserDetailsService,UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public CustomerUserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException(" user not found with email "+username);
		}
		
		USER_ROLE role=user.getRole();
		
		List<GrantedAuthority> authorities=new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
