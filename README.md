# Library Management System

## Prerequisites

- Java 8 or later

## Apps

The `main` branch contains the Library Management System is a Command Line Interface application.

### Run with CLI

Clone the project. Open it in your favorite IDE. Start the `Main` application.  
While doing this, a help message is printed to the console to display commands and options. Currently, there are
following Commands and Options:

```text
help                                 : Prints help message

// User commands
user-list                            : Lists all registered users in the library
user-add <firstName> <lastName>      : Register a new user in the library

// Book commands
book-list                            : Lists all the books in the library catalog
book-add <title>                     : Add a new book to library catalog. Example: book-add TCP/IP Illustrated
book-borrow <bookId> <userId>        : Borrow a book by an user (execute user-list or book-list for ids)
book-return <bookId>                 : Return the book to the library (execute book-list for ids)
book-borrowed <userId>               : Lists books borrowed by the user (see user-list for ids)
```