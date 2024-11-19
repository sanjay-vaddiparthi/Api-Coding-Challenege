package com.hexaware.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.model.Users;
import com.hexaware.repository.UsersRepository;

@Service
public class UserDetailServiceConfig implements UserDetailsService {

	@Autowired
	private UsersRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepo.findByUserName(username);
		if(user!=null) {
			return User.builder()
					.username(user.getUserName())
					.password(user.getPassword())
					.roles(getRoles(user.getRole()))
					.build();
		}
		else {
			throw new UsernameNotFoundException(username);
		}
	}
	
	private String getRoles(String role) {
		if(role==null) 
			return "ADMIN";
		return role;
	}

}

