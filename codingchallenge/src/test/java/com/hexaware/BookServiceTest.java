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
    void testGetAll_Success() {
        List<Books> books = Arrays.asList(
                new Books(1, "1234567890123", "Title1", "Author1", 2020),
                new Books(2, "1234567890124", "Title2", "Author2", 2021)
        );

        when(bookRepo.findAll()).thenReturn(books);
        when(modelMapper.map(any(Books.class), eq(BooksDTO.class)))
                .thenReturn(new BooksDTO(1, "1234567890123", "Title1", "Author1", 2020))
                .thenReturn(new BooksDTO(2, "1234567890124", "Title2", "Author2", 2021));

        List<BooksDTO> result = bookService.getall();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void testAddBook_Success() throws BookAlreadyExistsException {
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
    void testAddBook_AlreadyExists() {
        Books existingBook = new Books(1, "1234567890123", "Existing Title", "Existing Author", 2020);
        when(bookRepo.findByTitle("Existing Title")).thenReturn(existingBook);

        BooksDTO booksDTO = new BooksDTO(1, "1234567890123", "Existing Title", "Existing Author", 2020);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(booksDTO));
        verify(bookRepo, times(1)).findByTitle("Existing Title");
    }

    @Test
    void testAddBook_InvalidYear() {
        BooksDTO booksDTO = new BooksDTO(0, "1234567890123", "New Title", "New Author", LocalDate.now().getYear() + 1);

        assertThrows(ValidationException.class, () -> bookService.addBook(booksDTO));
    }

    @Test
    void testUpdateBook_Success() throws BookNotFoundException {
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
    void testUpdateBook_NotFound() {
        when(bookRepo.findById(1)).thenReturn(Optional.empty());

        BooksDTO updatedDTO = new BooksDTO(1, "1234567890123", "Updated Title", "Updated Author", 2021);

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1, updatedDTO));
        verify(bookRepo, times(1)).findById(1);
    }

    @Test
    void testGetBookByIsbn_Success() throws BookNotFoundException {
        Books book = new Books(1, "1234567890123", "Title1", "Author1", 2020);
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(book);
        when(modelMapper.map(book, BooksDTO.class)).thenReturn(new BooksDTO(1, "1234567890123", "Title1", "Author1", 2020));

        BooksDTO result = bookService.getbookbyisbn("1234567890123");

        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
    }

    @Test
    void testGetBookByIsbn_NotFound() {
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> bookService.getbookbyisbn("1234567890123"));
    }

    @Test
    void testDeleteBook_Success() throws BookNotFoundException {
        Books book = new Books(1, "1234567890123", "Title1", "Author1", 2020);
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(book);

        String result = bookService.deletebook("1234567890123");

        assertEquals("Book deleted", result);
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
        verify(bookRepo, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> bookService.deletebook("1234567890123"));
    }
}