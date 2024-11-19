package com.hexaware;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hexaware.Exception.BookAlreadyExistsException;
import com.hexaware.Exception.BookNotFoundException;
import com.hexaware.dto.BooksDTO;
import com.hexaware.model.Books;
import com.hexaware.repository.BookRepo;
import com.hexaware.service.BookService;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addbookTest() throws BookAlreadyExistsException {
        BooksDTO booksDTO = new BooksDTO(0, "1234567890123", "New Title", "New Author", 2022);
        Books book = new Books(0, "1234567890123", "New Title", "New Author", 2022);

        when(bookRepo.findByTitle("New Title")).thenReturn(null);
        when(modelMapper.map(booksDTO, Books.class)).thenReturn(book);
        when(bookRepo.save(book)).thenReturn(book);
        when(modelMapper.map(book, BooksDTO.class)).thenReturn(booksDTO);

        BooksDTO result = bookService.addBook(booksDTO);

        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        verify(bookRepo, times(1)).findByTitle("New Title");
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void bookalreadyexistTest() {
        Books existingBook = new Books(1, "1234567890123", "Existing Title", "Existing Author", 2020);
        when(bookRepo.findByTitle("Existing Title")).thenReturn(existingBook);

        BooksDTO booksDTO = new BooksDTO(1, "1234567890123", "Existing Title", "Existing Author", 2020);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(booksDTO));
        verify(bookRepo, times(1)).findByTitle("Existing Title");
    }

    @Test
    void updatebookTest() throws BookNotFoundException {
        Books existingBook = new Books(1, "1234567890123", "Old Title", "Old Author", 2019);
        BooksDTO updatedDTO = new BooksDTO(1, "1234567890123", "Updated Title", "Updated Author", 2021);

        when(bookRepo.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepo.save(existingBook)).thenReturn(existingBook);
        when(modelMapper.map(existingBook, BooksDTO.class)).thenReturn(updatedDTO);

        BooksDTO result = bookService.updateBook(1, updatedDTO);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(bookRepo, times(1)).findById(1);
        verify(bookRepo, times(1)).save(existingBook);
    }

   
    @Test
    void getbyisbnTest() throws BookNotFoundException {
        Books book = new Books(1, "1234567890123", "Title1", "Author1", 2020);
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(book);
        when(modelMapper.map(book, BooksDTO.class)).thenReturn(new BooksDTO(1, "1234567890123", "Title1", "Author1", 2020));

        BooksDTO result = bookService.getbookbyisbn("1234567890123");

        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
    }

    @Test
    void deletebookTest() throws BookNotFoundException {
        Books book = new Books(1, "1234567890123", "Title1", "Author1", 2020);
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(book);

        String result = bookService.deletebook("1234567890123");

        assertEquals("Book deleted", result);
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
        verify(bookRepo, times(1)).delete(book);
    }

}