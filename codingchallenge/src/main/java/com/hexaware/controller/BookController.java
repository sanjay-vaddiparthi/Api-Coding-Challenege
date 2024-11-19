package com.hexaware.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hexaware.Exception.BookAlreadyExistsException;
import com.hexaware.Exception.BookNotFoundException;
import com.hexaware.dto.BooksDTO;
import com.hexaware.service.BookService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/getall")
    public ResponseEntity<List<BooksDTO>> getAllBooks() {
        List<BooksDTO> books = bookService.getall();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/byid/{isbn}")
    public ResponseEntity<BooksDTO> getBookByIsbn(@PathVariable String isbn) {
        try {
            BooksDTO book = bookService.getbookbyisbn(isbn);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<BooksDTO> addBook(@RequestBody @Valid BooksDTO booksDTO) {
        try {
            BooksDTO createdBook = bookService.addBook(booksDTO);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (BookAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/update/{bookid}")
    public ResponseEntity<BooksDTO> updateBook(@PathVariable int bookid, @RequestBody @Valid BooksDTO booksDTO) {
        try {
            BooksDTO updatedBook = bookService.updateBook(bookid, booksDTO);
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deletebyid/{isbn}")
    public ResponseEntity<String> deleteByIsbn(@PathVariable String isbn) throws BookNotFoundException {
            bookService.deletebook(isbn);
            return ResponseEntity.ok("Book Deleted");
       
}
}
