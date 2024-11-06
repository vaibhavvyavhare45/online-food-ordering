package com.warrior.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warrior.model.Address;

public interface AddressRepository  extends JpaRepository<Address, Long>{

}
