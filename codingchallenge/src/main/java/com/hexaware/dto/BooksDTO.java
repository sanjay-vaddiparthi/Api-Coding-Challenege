package com.hexaware.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BooksDTO {
	private int bookid;
	
	@NotNull(message = "ISBN cannot be null")
	@Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters long")
	@Pattern(regexp = "^[A-Za-z0-9]{13}$", message = "ISBN must be a valid 13-character value")
    private String isbn;
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;
    @NotNull(message = "Author cannot be null")
    @Size(min = 1, max = 100, message = "Author name must be between 1 and 100 characters")
    private String author;
    @Min(value = 1500, message = "Year must be no earlier than 1900")
    @Max(value = 2024, message = "Year must not exceed the current year")
    private int publicationYear;
    
	public BooksDTO() {
		super();
	}

	public BooksDTO(int bookid, String isbn, String title, String author, int publicationYear) {
		super();
		this.bookid = bookid;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	@Override
	public String toString() {
		return "BooksDTO [bookid=" + bookid + ", isbn=" + isbn + ", title=" + title + ", author=" + author
				+ ", publicationYear=" + publicationYear + "]";
	}
    
	
    

}
