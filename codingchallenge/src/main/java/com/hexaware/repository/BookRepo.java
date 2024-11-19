package com.hexaware.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.model.Books;


@Repository
public interface BookRepo extends JpaRepository<Books,Integer> {

	Books findByIsbn(String isbn);
	Books findByTitle(String title);
	

}
