package com.warrior.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.warrior.dto.RestaurentDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	
	private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
	private List<Order> orders=new ArrayList<>();
	
	@ElementCollection
	private List<RestaurentDto> favorites=new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Address> addresses=new ArrayList<>();
	
	
	

}
