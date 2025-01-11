package com.example.rentalfilm.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findByEmail(String email);

    void save(User user);
}
