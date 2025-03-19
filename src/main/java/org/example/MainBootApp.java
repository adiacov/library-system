package org.example;

import org.example.model.Book;
import org.example.model.User;
import org.example.repository.BookRepository;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainBootApp {

    public static void main(String[] args) {
        SpringApplication.run(MainBootApp.class, args);
        System.out.println("Hello");
    }

    // Insert some data into DB
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, BookRepository bookRepo) {
        return args -> {
            userRepo.save(new User("John", "Doe"));
            bookRepo.save(new Book("TCP/IP Illustrated"));
        };
    }
}
