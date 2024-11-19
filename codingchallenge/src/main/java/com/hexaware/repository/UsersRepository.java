package com.hexaware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
	Users findByUserName(String userName);

}
