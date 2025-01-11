package com.example.rentalfilm.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public boolean registerUser(User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                return false; // Email already exists
            }
            user.setPass(user.getPass());
            user.setRole("user");
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPass().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

}