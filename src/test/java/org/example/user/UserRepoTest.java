package org.example.user;

import org.example.model.User;
import org.example.repository.InMemoryUserRepo;
import org.example.repository.UserRepo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepoTest {

    private UserRepo repo = new InMemoryUserRepo();
    private final User john = new User(1, "John", "Doe");
    private final User jane = new User(2, "Jane", "Doe");

    @Test
    void testFindUser() {
        repo.addUser(john.getFirstName(), john.getLastName());
        Optional<User> maybeUser = repo.findUser(john.getId());

        assertTrue(maybeUser.isPresent());
        User user = maybeUser.get();
        assertEquals(john, user);
    }

    @Test
    void testFindAllUsers() {
        repo.addUser(john.getFirstName(), john.getLastName());
        repo.addUser(jane.getFirstName(), jane.getLastName());
        List<User> users = repo.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(repo.findUser(john.getId()).isPresent());
        assertTrue(repo.findUser(jane.getId()).isPresent());

        assertEquals(john, repo.findUser(john.getId()).get());
        assertEquals(jane, repo.findUser(jane.getId()).get());
    }
}