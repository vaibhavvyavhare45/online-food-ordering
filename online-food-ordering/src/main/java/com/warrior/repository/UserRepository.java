package com.warrior.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warrior.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByEmail(String userName);

}
