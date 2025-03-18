package org.example.user;

import java.util.List;
import java.util.Optional;

public interface UserRepo {

    public long addUser(String firstName, String lastName);

    public Optional<User> findUser(long id);

    public List<User> getAllUsers();
}
