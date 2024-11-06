package com.warrior.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrior.config.JwtProvider;
import com.warrior.model.User;
import com.warrior.repository.UserRepository;
import com.warrior.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
		super();
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		User user=findUserByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user=userRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("User not found");
		}
		return user;
	}
	

}
