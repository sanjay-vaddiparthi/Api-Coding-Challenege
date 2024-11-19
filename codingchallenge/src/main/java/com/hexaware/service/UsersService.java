package com.hexaware.service;

import java.time.LocalDate;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.dto.BooksDTO;
import com.hexaware.dto.UsersDTO;
import com.hexaware.Exception.BookNotFoundException;
import com.hexaware.Exception.UserNameAlreadyExistsException;
import com.hexaware.Exception.UserNameNotFoundException;
import com.hexaware.model.Books;
import com.hexaware.model.Users;
import com.hexaware.repository.UsersRepository;


import jakarta.validation.ValidationException;


@Service
public class UsersService{

	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	ModelMapper model;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public UsersDTO signUp(UsersDTO userDto) throws UserNameAlreadyExistsException {

		Users user=usersRepo.findByUserName(userDto.getUserName());
		if(user!=null) {
			throw new UserNameAlreadyExistsException("Username already exists :"+userDto.getUserName());
		}
		
		user = model.map(userDto, Users.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user=usersRepo.save(user);
		return model.map(user, UsersDTO.class);
	}

	public UsersDTO updatePassword(UsersDTO userDto) throws UserNameNotFoundException {
		Users user = usersRepo.findByUserName(userDto.getUserName());
		if(user==null) {
			throw new UserNameNotFoundException("Username not found :"+userDto.getUserName());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = usersRepo.save(user);
		return model.map(user, UsersDTO.class);
	}

}
