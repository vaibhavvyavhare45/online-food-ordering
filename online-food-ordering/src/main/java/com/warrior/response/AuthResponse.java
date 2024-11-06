package com.warrior.response;

import com.warrior.model.USER_ROLE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthResponse {
	private String jwt;
	private String message;
	private USER_ROLE role;

}
