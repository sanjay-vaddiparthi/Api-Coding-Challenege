package com.hexaware.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.dto.UsersDTO;
import com.hexaware.Exception.UserNameAlreadyExistsException;
import com.hexaware.Exception.UserNameNotFoundException;
import com.hexaware.service.UsersService;
import com.hexaware.webtoken.JwtService;
import com.hexaware.webtoken.LoginForm;

@RestController
@RequestMapping("/user")
public class UsersController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticateManager;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<UsersDTO> signUp(@RequestBody UsersDTO userDto) throws UserNameAlreadyExistsException{
		UsersDTO user = userService.signUp(userDto);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<UsersDTO> update(@RequestBody UsersDTO userDto) throws UserNameNotFoundException{
		UsersDTO user = userService.updatePassword(userDto);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication =	authenticateManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginForm.username(),
						loginForm.password()
						)
				);
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(userDetailService.loadUserByUsername(loginForm.username()));
		}
		else {
			throw new UsernameNotFoundException("Invalid Login");
		}
	}
}
