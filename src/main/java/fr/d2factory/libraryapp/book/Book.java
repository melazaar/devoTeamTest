package fr.d2factory.libraryapp.book;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * A simple representation of a book
 */
public class Book {

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("isbn")
    private ISBN isbn;

    private LocalDate borrowedAt;

    public Book() {
    }

    public Book(String title, String author, ISBN isbn, LocalDate borrowedAt) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.borrowedAt = borrowedAt;
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

    public ISBN getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

    public LocalDate getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(LocalDate borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn=" + isbn +
                '}';
    }
}
