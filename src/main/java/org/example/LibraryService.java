package org.example;

import org.example.book.Book;
import org.example.book.BookRepo;
import org.example.user.User;
import org.example.user.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
Note: many service methods return SUCCESS or ERROR strings.
Normally in a real world application, this would a different type or Exception (in case of errors).
For now keeping it simple.
 */
public class LibraryService {

    private static final Logger appLogger = Logger.getLogger(LibraryService.class.getName());
    // create a logger for clean line printing
    private static Logger lineLogger = Logger.getAnonymousLogger();

    static {
        lineLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(java.util.logging.LogRecord record) {
                return record.getMessage() + "\n";
            }
        });
        lineLogger.addHandler(handler);
    }

    private final UserRepo userRepo;
    private final BookRepo bookRepo;

    /* To keep it simpler, LibraryService is initiated with repositories.
     * In a real (or more complex system),
     * LibraryService constructor parameters would be a UserService and a BookService.
     */
    public LibraryService(UserRepo userRepo, BookRepo bookRepo) {
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    // Assume all data is valid.
    public String registerUser(String firstName, String lastName) {
        long id = userRepo.addUser(firstName, lastName);
        return "SUCCESS: Registered new user with id " + id;
    }

    public String listUsers() {
        String result = "Library Users:\n";
        for (User user : userRepo.getAllUsers()) {
            result = result.concat(user.toString() + '\n');
        }
        return result;
    }

    public String listBooks() {
        List<Book> books = bookRepo.getAllBooks();
        String result = "Books Catalog:\n";
        for (Book book : books) {
            result = result.concat(book.toString() + '\n');
        }
        return result;
    }

    public String addNewBook(String title) {
        bookRepo.addBook(title);
        return "SUCCESS: Added new book to library catalog: " + title;
    }

    public String borrowBook(long bookId, long userId) {
        Optional<Book> maybeBook = bookRepo.findBook(bookId);
        if (maybeBook.isPresent()) {
            Book book = maybeBook.get();
            if (book.getBorrowed().isPresent()) {
                return "ERROR: Book already borrowed";
            } else {
                Book borrowed = book.borrow(userId);
                bookRepo.updateBook(borrowed);
                return "SUCCESS: Book borrowed: " + borrowed.getTitle();
            }
        } else {
            return "ERROR: Book doesn't exists in library's catalog.";
        }
    }

    public String returnBook(long bookId) {
        Optional<Book> maybeBook = bookRepo.findBook(bookId);
        if (maybeBook.isPresent()) {
            Book book = maybeBook.get().unBorrow();
            bookRepo.updateBook(book);
            return "SUCCESS: Book returned " + book.getTitle();
        } else {
            return "ERROR: Cannot return non existent book in the catalog.";
        }
    }

    public String listBorrowedBooks(long userId) {
        List<Book> borrowed = bookRepo.findBorrowedBooks(userId);
        if (borrowed.isEmpty()) {
            return "SUCCESS: User has no borrowed books.";
        } else {
            String result = "User borrowed the following books:\n";
            for (Book book : borrowed) {
                result = result.concat(book.getTitle() + "\n");
            }
            return "SUCCESS: " + result;
        }
    }
}
