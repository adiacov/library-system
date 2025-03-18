package org.example.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryBookRepo implements BookRepo {

    private List<Book> db = new ArrayList<>();
    private AtomicLong idGen = new AtomicLong();

    @Override
    public long addBook(String title) {
        long id = idGen.incrementAndGet();
        db.add(new Book(id, title));
        return id;
    }

    @Override
    public long updateBook(Book book) {
        findBook(book.getId()).ifPresent(maybeBook -> db.remove(maybeBook));
        db.add(book);
        return book.getId();
    }

    @Override
    public Optional<Book> findBook(long id) {
        return db.stream().filter(book -> book.getId() == id).findFirst();
    }

    @Override
    public List<Book> getAllBooks() {
        return db;
    }

    @Override
    public List<Book> findBorrowedBooks(long userId) {
        return db.stream()
                .filter(book ->
                        book.getBorrowed().isPresent() &&
                                book.getBorrowed().get() == userId)
                .collect(Collectors.toList());
    }
}
