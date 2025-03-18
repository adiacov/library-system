package org.example.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepo implements UserRepo {

    private List<User> db = new ArrayList<>();
    private AtomicLong idGen = new AtomicLong();

    @Override
    public long addUser(String firstName, String lastName) {
        Long nextId = idGen.incrementAndGet();
        User user = new User(nextId, firstName, lastName);
        db.add(user);
        return nextId;
    }

    @Override
    public Optional<User> findUser(long id) {
        return db.stream().filter(user -> user.getId() == id).findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return db;
    }
}
