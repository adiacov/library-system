package org.example.book;

import java.util.List;
import java.util.Optional;

public interface BookRepo {

    public long addBook(String title);
    public long updateBook(Book book);
    public Optional<Book> findBook(long id);
    public List<Book> getAllBooks();
    public List<Book> findBorrowedBooks(long userId);
}
