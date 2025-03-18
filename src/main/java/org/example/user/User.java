package org.example.user;

public class User {
    private long id;
    private String firstName;
    private String lastName;

    public User(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;
        return this.firstName == other.firstName && this.lastName == other.lastName;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, firstName=%s, lastName=%s]", this.id, this.firstName, this.lastName);
    }
}
