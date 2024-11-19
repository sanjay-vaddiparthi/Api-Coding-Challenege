package com.hexaware.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.Exception.BookAlreadyExistsException;
import com.hexaware.Exception.BookNotFoundException;
import com.hexaware.dto.BooksDTO;
import com.hexaware.model.Books;
import com.hexaware.repository.BookRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class BookService {
	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<BooksDTO> getall(){
		List<Books> book=bookRepo.findAll();
		List<BooksDTO> BookDto=new ArrayList<>();
		
		for(Books books:book) {
			BookDto.add(modelMapper.map(books,BooksDTO.class));
		}
		return BookDto;
	}
	
	public BooksDTO addBook(BooksDTO booksDTO) throws BookAlreadyExistsException{
		Books book=bookRepo.findByTitle(booksDTO.getTitle());
		if (book!=null) {
	        throw new BookAlreadyExistsException("A book with the title '" + booksDTO.getTitle() + "' already exists.");
	    }
		if (booksDTO.getPublicationYear() > LocalDate.now().getYear()) {
		    throw new ValidationException("Year must be in the past or present");
		}
		book=modelMapper.map(booksDTO,Books.class);
		Books savedbook=bookRepo.save(book);
		return modelMapper.map(savedbook, BooksDTO.class);
	}
	
	public BooksDTO updateBook(int bookid,BooksDTO bdetails) throws BookNotFoundException {
		Books book=bookRepo.findById(bookid).orElseThrow(()-> new BookNotFoundException("Book with"+ bookid + "not found"));
		book.setIsbn(bdetails.getIsbn());
		book.setTitle(bdetails.getTitle());
		book.setAuthor(bdetails.getAuthor());
		book.setPublicationYear(bdetails.getPublicationYear());
		
		Books upbook=bookRepo.save(book);
		return modelMapper.map(upbook,BooksDTO.class);
	}
	
	public BooksDTO getbookbyisbn(String isbn) throws BookNotFoundException {
	    Books book = bookRepo.findByIsbn(isbn);
	    if(book==null) {
	    	throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
	    }
	    return modelMapper.map(book, BooksDTO.class);
	}
	
	public String deletebook(String isbn) throws BookNotFoundException {
		Books book=bookRepo.findByIsbn(isbn);
		if (book==null) {
	        throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
	    }
		bookRepo.delete(book);
		return "Book deleted";
	}
	

}
