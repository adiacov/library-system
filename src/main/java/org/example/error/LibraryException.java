package org.example.error;

public class LibraryException extends RuntimeException {
    public LibraryException(String message) {
        super(message);
    }

    public static class BookNotFoundException extends LibraryException {
        public BookNotFoundException(long bookId) {
            super("Book with id " + bookId + " not found");
        }
    }

    public static class BookAlreadyBorrowedException extends LibraryException {
        public BookAlreadyBorrowedException(long bookId) {
            super("Book with id " + bookId + " is already borrowed");
        }
    }
}
