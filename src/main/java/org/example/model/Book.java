package org.example.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    /**
     * The ID of the user who borrowed the book.
     * <p>
     * If null, the book is not borrowed.
     * If not null, it contains the ID of the user who borrowed the book.
     */
    private Long borrowed;

    // TODO - clean , remove constructors

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(long id, String title) {
        this.id = id;
        this.title = title;
        this.borrowed = null; // By default, the book is not borrowed
    }

    // This constructor is needed for the CLI application
    public Book(long id, String title, Long borrowed) {
        this.id = id;
        this.title = title;
        this.borrowed = borrowed;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Optional<Long> getBorrowed() {
        return Optional.ofNullable(borrowed);
    }

    public Book borrow(Long userId) {
        return new Book(this.id, this.title, userId);
    }

    public Book returnBook() {
        return new Book(this.id, this.title, null);
    }

    public boolean isBorrowed() {
        return Optional.ofNullable(this.borrowed).isPresent();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Book)) {
            return false;
        }

        Book other = (Book) obj;
        return this.id == other.getId() &&
                this.title == other.getTitle();
    }

    @Override
    public String toString() {
        String availability = Optional.ofNullable(borrowed)
                .map(userId -> "borrowed by user " + userId)
                .orElse("available");
        return String.format("Book[id=%d, title=%s, %s]", this.id, this.title, availability);
    }
}
