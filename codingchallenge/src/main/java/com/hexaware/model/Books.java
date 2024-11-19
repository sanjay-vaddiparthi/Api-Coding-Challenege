package com.hexaware.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Books {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int bookid;
    private String isbn; 
    private String title;
    private String author;
    private int publicationYear;
    
	public Books() {
		super();
	}

	public Books(int bookid, String isbn, String title, String author, int publicationYear) {
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
		return "Books [bookid=" + bookid + ", isbn=" + isbn + ", title=" + title + ", author=" + author
				+ ", publicationYear=" + publicationYear + "]";
	}
	
	
    
    
    
    
    
    

}
