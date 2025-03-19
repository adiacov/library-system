# Library Management System

## Task

/******************* Library Kata ********************/  
You can use any language you like; when in doubt choose the SIMPLEST option.  
I would like the following features in my library application:

1. Add a new book to the catalog
2. A user can borrow a book
3. A user can return a book
4. Print a list of the books that I borrowed

## Prerequisites

- Java 17 or later
- Gradle
- IDE of choice

## App

As the requirements didn't specify what kind of application it should be, the result is 2 applications. The applications
themselves are as simple as possible, where there is no or little validation (mostly for the CLI app). It's clear that
in real
life, the library system would be more complicated, constituted from multiple services each executing its own part.
The first is a CLI-based application and the second is an application using Spring Boot.  
The main entry point for the CLI app is the `MainCLIApp` and for the Spring Boot this is the `MainBootApp`.

### Run the CLI Application

Navigate to the `MainCLIApp`.  
When started, a help message is printed to the console to display commands and options. Navigate to the command line
terminal to be able to input commands. Certain commands require arguments, so be sure to read the help message
carefully.  
Currently, there are the following Commands and Options:

```text
help                                 : Prints help message

// User commands
user-list                            : Lists all registered users in the library
user-add <firstName> <lastName>      : Register a new user in the library

// Book commands
book-list                            : Lists all the books in the library catalog
book-add <title>                     : Add a new book to library catalog. Example: book-add "TCP/IP Illustrated"
book-borrow <bookId> <userId>        : Borrow a book by a user (execute user-list or book-list for ids)
book-return <bookId>                 : Return the book to the library (execute book-list for ids)
book-borrowed <userId>               : Lists books borrowed by the user (see user-list for ids)
```

### Run the Spring Boot Application

Navigate to the MainBootApp.
When started, the application exposes endpoints at localhost:8080.
You can either execute curl commands one by one, or you can import library-system-api.json (location: project root) into
Postman directly.

```json
// Get all registered users
curl --location 'localhost:8080/users'

// Register a new user
curl --location 'localhost:8080/users/register' \
--header 'Content-Type: application/json' \
--data '{"firstName": "Jane", "lastName": "Doe"}'

// Get all books in the library.
// If userId is provided as a query parameter, get all books borrowed by this user.
curl --location 'localhost:8080/books'

curl --location 'localhost:8080/books?userId=1'

// Create a new book in library.
curl --location 'localhost:8080/books/add' \
--header 'Content-Type: application/json' \
--data '{"title": "The Key to Success"}'

// Borrow a book for a specific user.
curl --location 'localhost:8080/books/1/borrow' \
--header 'Content-Type: application/json' \
--data '{"userId": 1}'

// Return a book back to the library.
curl --location --request POST 'localhost:8080/books/1/return'
```