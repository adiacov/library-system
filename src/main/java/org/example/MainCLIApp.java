package org.example;

import org.example.repository.BookRepo;
import org.example.repository.InMemoryBookRepo;
import org.example.service.LibraryServiceCli;
import org.example.repository.InMemoryUserRepo;
import org.example.repository.UserRepo;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainCLIApp {
    // logger for app processes
    private final static Logger appLogger = Logger.getLogger(MainCLIApp.class.getName());

    // logger for simple messages (w/o log format).
    // this is done to avoid situation when System.out.println doesn't display anything while running the app.
    private static Logger lineLogger = Logger.getAnonymousLogger();

    // remove default log formatting. print only messages.
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

    // Initialization
    private static UserRepo userRepo = new InMemoryUserRepo();
    private static BookRepo bookRepo = new InMemoryBookRepo();
    private static LibraryServiceCli library = new LibraryServiceCli(userRepo, bookRepo);

    public static void main(String[] args) {
        // Populate some data
        userRepo.addUser("John", "Doe"); // id=1
        bookRepo.addBook("TCP/IP Illustrated"); // id=1

        // Command line app
        Scanner scanner = new Scanner(System.in);
        lineLogger.info("Running Library App.");
        lineLogger.info("Enter a command or 'exit' to quit.");
        executeHelp();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("exit")) {
                lineLogger.info("Exiting Library App.");
                break;
            }

            // Process commands
            processCommand(input);
        }
        scanner.close();
    }

    private static void processCommand(String input) {
        String[] parts = input.split("\\s+"); // Split into command and arguments.
        String command = parts[0]; // arguments start from index 1. Unused arguments are skipped.
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        switch (command) {
            case ("help"):
                executeHelp();
                break;

            case ("user-list"):
                executeUserList();
                break;

            case ("user-add"):
                executeUserAdd(args);
                break;

            case ("book-list"):
                executeBookList();
                break;

            case ("book-add"):
                executeBookAdd(args);
                break;

            case ("book-borrow"):
                executeBookBorrow(args);
                break;

            case ("book-return"):
                executeBookReturn(args);
                break;

            case ("book-borrowed"):
                executeBookBorrowed(args);
                break;

            default:
                lineLogger.warning("WARNING: Unsupported command " + command);
                break;
        }
    }

    private static void executeBookBorrowed(String[] args) {
        // this command expects 1 argument.
        if (args.length < 1) {
            String msg = String.format("Command 'book-borrowed' expects 1 argument. Currently there are %d arguments. Try again or 'exit'.", args.length);
            appLogger.warning(msg);
        } else {
            try {
                String result = library.listBorrowedBooks(Long.parseLong(args[0]));
                lineLogger.info(result);
            } catch (NumberFormatException ex) {
                String msg = "Cannot parse arguments. Command 'book-borrowed' expects number as argument.";
                appLogger.warning(msg);
            }
        }
    }

    private static void executeBookReturn(String[] args) {
        // this command expects 1 argument.
        if (args.length < 1) {
            String msg = String.format("Command 'book-borrow' expects 1 argument. Currently there are %d arguments. Try again or 'exit'.", args.length);
            appLogger.warning(msg);
        } else {
            try {
                String result = library.returnBook(Long.parseLong(args[0]));
                lineLogger.info(result);
            } catch (NumberFormatException ex) {
                String msg = "Cannot parse arguments. Command 'book-borrow' expects number as argument.";
                appLogger.warning(msg);
            }
        }
    }

    private static void executeBookBorrow(String[] args) {
        // this command expects 2 arguments.
        if (args.length < 2) {
            String msg = String.format("Command 'book-borrow' expects 2 arguments. Currently there are %d arguments. Try again or 'exit'.", args.length);
            appLogger.warning(msg);
        } else {
            try {
                String result = library.borrowBook(Long.parseLong(args[0]), Long.parseLong(args[1]));
                lineLogger.info(result);
            } catch (NumberFormatException ex) {
                String msg = "Cannot parse arguments. Command 'book-borrow' expects numbers as arguments.";
                appLogger.warning(msg);
            }
        }
    }

    private static void executeBookAdd(String[] args) {
        // this command expects one argument
        if (args.length < 1) {
            String msg = String.format("Command 'book-add' expects 1 argument. Currently there are %d arguments. Try again or 'exit'.", args.length);
            appLogger.warning(msg);
            return;
        }
        String result = library.addNewBook(args[0]);
        lineLogger.info(result);
    }

    private static void executeBookList() {
        String result = library.listBooks();
        lineLogger.info(result);
    }

    private static void executeUserAdd(String[] args) {
        // this command expects 2 arguments.
        if (args.length < 2) {
            String msg = String.format("Command 'user-add' expects 2 arguments. Currently there are %d arguments. Try again or 'exit'.", args.length);
            appLogger.warning(msg);
            return;
        }
        String result = library.registerUser(args[0], args[1]);
        lineLogger.info(result);
    }

    private static void executeUserList() {
        String result = library.listUsers();
        lineLogger.info(result);
    }

    private static void executeHelp() {
        lineLogger.info("Options:");
        // Utils
        lineLogger.info("help                                 : Prints help message.");

        // User commands
        lineLogger.info("user-list                            : Lists all registered users in the library.");
        lineLogger.info("user-add <firstName> <lastName>      : Register a new user in the library.");

        // Book commands
        lineLogger.info("book-list                            : Lists all the books in the library catalog.");
        lineLogger.info("book-add <title>                     : Add a new book to library catalog. Example: book-add TCP/IP Illustrated.");
        lineLogger.info("book-borrow <bookId> <userId>        : Borrow a book by an user (execute user-list or book-list for ids).");
        lineLogger.info("book-return <bookId>                 : Return the book to the library (execute book-list for ids).");
        lineLogger.info("book-borrowed <userId>               : Lists books borrowed by the user (see user-list for ids).");
        lineLogger.info("-----------------------------------");
    }
}