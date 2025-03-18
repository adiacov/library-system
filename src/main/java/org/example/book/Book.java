package org.example.book;

import java.util.Optional;

public class Book {

    private long id;
    private String title;
    /**
     * The ID of the user who borrowed the book.
     * <p>
     * If empty, the book is not borrowed.
     * If non-empty, it contains the ID of the user who borrowed the book.
     */
    private Optional<Long> borrowed;

    public Book(long id, String title) {
        this.id = id;
        this.title = title;
        this.borrowed = Optional.empty();
    }

    public Book(long id, String title, Optional<Long> borrowed) {
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
        return borrowed;
    }

    public Book borrow(Long userId) {
        return new Book(this.id, this.title, Optional.of(userId));
    }

    public Book unBorrow() {
        return new Book(this.id, this.title, Optional.empty());
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
        String availability = borrowed.map(userId -> "borrowed by user " + userId).orElse("available");
        return String.format("Book[id=%d, title=%s, %s]", this.id, this.title, availability);
    }
}
