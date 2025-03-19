package org.example.service;

import org.example.model.Book;
import org.example.error.LibraryException;
import org.example.model.User;
import org.example.repository.BookRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceBoot {

    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    @Autowired
    public LibraryServiceBoot(BookRepository bookRepo, UserRepository userRepo) {
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User registerUser(User user) {
        return userRepo.save(user);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book createBook(Book book) {
        return bookRepo.save(book);
    }

    public Book borrowBook(long bookId, long userId) {
        Optional<Book> maybeBook = bookRepo.findById(bookId);
        if (maybeBook.isEmpty()) {
            throw new LibraryException.BookNotFoundException(bookId);
        }

        Book book = maybeBook.get();
        if (book.isBorrowed()) {
            throw new LibraryException.BookAlreadyBorrowedException(bookId);
        }

        Book borrowed = book.borrow(userId);
        bookRepo.save(borrowed);
        return borrowed;
    }

    public Book returnBook(Long bookId) {
        Optional<Book> maybeBook = bookRepo.findById(bookId);
        if (maybeBook.isEmpty()) {
            throw new LibraryException.BookNotFoundException(bookId);
        }

        Book book = maybeBook.get().returnBook();
        bookRepo.save(book);
        return book;
    }

    public List<Book> getBorrowedBooks(long userId) {
        return bookRepo.findByBorrowed(userId);
    }
}
