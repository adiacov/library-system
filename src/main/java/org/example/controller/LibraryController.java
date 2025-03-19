package org.example.controller;

import org.example.model.Book;
import org.example.error.LibraryException;
import org.example.service.LibraryServiceBoot;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    private LibraryServiceBoot libraryService;

    public LibraryController(LibraryServiceBoot libraryService) {
        this.libraryService = libraryService;
    }

    // Get all registered users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return libraryService.getAllUsers();
    }

    // Register a new user
    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = libraryService.registerUser(user);
        return ResponseEntity.status(201).body(newUser);
    }

    // Get all books in the library.
    // If userId is provided as a query parameter, get all books borrowed by this user.
    @GetMapping("/books")
    public List<Book> getAllBooks(@RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return libraryService.getAllBooks();
        } else {
            return libraryService.getBorrowedBooks(userId);
        }
    }

    // Create n new book in library.
    @PostMapping("/books/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = libraryService.createBook(book);
        return ResponseEntity.status(201).body(newBook);
    }

    // Borrow a book for a specific user.
    @PostMapping("/books/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable("bookId") Long bookId, @RequestBody BorrowRequest req) {
        try {
            Book book = libraryService.borrowBook(bookId, req.getUserId());
            return ResponseEntity.status(200).body(book);
        } catch (LibraryException.BookNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (LibraryException.BookAlreadyBorrowedException ex) {
            return ResponseEntity.status(409).body(ex.getMessage());
        }
    }

    // Return a book back to the library.
    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<?> borrowBook(@PathVariable("bookId") Long bookId) {
        try {
            Book book = libraryService.returnBook(bookId);
            return ResponseEntity.status(200).body(book);
        } catch (LibraryException.BookNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

}
