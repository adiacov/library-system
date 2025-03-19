package org.example.book;

import org.example.model.Book;
import org.example.repository.BookRepo;
import org.example.repository.InMemoryBookRepo;
import org.example.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookRepoTest {

    // TODO:
    // add test -> you can insert multiple books with the same name
    // add test -> multiple same books, borrow one, one remains

    private BookRepo repo = new InMemoryBookRepo();
    private final Book techBook = new Book(1, "TCP/IP Illustrated");
    private final Book artBook = new Book(2, "The art of war");

    private final User john = new User(1, "John", "Doe");
    private final User jane = new User(2, "Jane", "Doe");

    @Test
    void testFindBook() {
        repo.addBook(techBook.getTitle());
        Optional<Book> maybeBook = repo.findBook(techBook.getId());

        assertTrue(maybeBook.isPresent());
        assertEquals(techBook, maybeBook.get());
    }

    @Test
    void testUpdateBook() {
        repo.addBook(techBook.getTitle());

        Optional<Book> originalBook = repo.findBook(techBook.getId());
        assertTrue(originalBook.isPresent());
        assertEquals(Optional.empty(), originalBook.get().getBorrowed());

        Book updated = techBook.borrow(111L);
        repo.updateBook(updated);
        Optional<Book> updatedBook = repo.findBook(techBook.getId());
        assertTrue(updatedBook.isPresent());
        assertEquals(Optional.of(111L), updatedBook.get().getBorrowed());

    }

    @Test
    void testFindAllBooks() {
        repo.addBook(techBook.getTitle());
        repo.addBook(artBook.getTitle());

        assertEquals(2, repo.getAllBooks().size());
        assertTrue(repo.findBook(techBook.getId()).isPresent());
        assertTrue(repo.findBook(artBook.getId()).isPresent());
        assertEquals(techBook, repo.findBook(techBook.getId()).get());
        assertEquals(artBook, repo.findBook(artBook.getId()).get());
    }

    @Test
    void testFindBorrowedBooksOneUserOneBook() {
        repo.addBook(techBook.getTitle());
        repo.addBook(artBook.getTitle());

        Book updatedBook = techBook.borrow(john.getId());
        repo.updateBook(updatedBook);

        List<Book> borrowedBooks = repo.findBorrowedBooks(john.getId());
        assertEquals(1, borrowedBooks.size());
        assertTrue(borrowedBooks.get(0).getBorrowed().isPresent());
        assertEquals(john.getId(), borrowedBooks.get(0).getBorrowed().get());
    }

    @Test
    void testFindBorrowedBooksOneUserMultipleBooks() {
        repo.addBook(techBook.getTitle());
        repo.addBook(artBook.getTitle());

        Book borrowed1 = techBook.borrow(john.getId());
        Book borrowed2 = artBook.borrow(john.getId());
        repo.updateBook(borrowed1);
        repo.updateBook(borrowed2);

        List<Book> borrowedBooks = repo.findBorrowedBooks(john.getId());
        assertEquals(2, borrowedBooks.size());
        List<Optional<Long>> borrowedIds = borrowedBooks.stream().map(Book::getBorrowed).collect(Collectors.toList());
        assertTrue(borrowedIds.stream().allMatch(id -> id.isPresent() && id.get() == john.getId()));
    }

    @Test
    void testFindBorrowedBooksMultipleUsers() {
        repo.addBook(techBook.getTitle());
        repo.addBook(artBook.getTitle());

        Book borrowed1 = techBook.borrow(john.getId());
        Book borrowed2 = artBook.borrow(jane.getId());

        repo.updateBook(borrowed1);
        repo.updateBook(borrowed2);

        assertEquals(2, repo.getAllBooks().size());

        List<Book> johnBooks = repo.findBorrowedBooks(john.getId());
        assertEquals(1, johnBooks.size());
        List<Optional<Long>> johnIds = johnBooks.stream().map(Book::getBorrowed).collect(Collectors.toList());
        assertTrue(johnIds.stream().allMatch(id -> id.isPresent() && id.get() == john.getId()));

        List<Book> janeBooks = repo.findBorrowedBooks(jane.getId());
        assertEquals(1, janeBooks.size());
        List<Optional<Long>> janeIds = janeBooks.stream().map(Book::getBorrowed).collect(Collectors.toList());
        assertTrue(janeIds.stream().allMatch(id -> id.isPresent() && id.get() == jane.getId()));
    }
}