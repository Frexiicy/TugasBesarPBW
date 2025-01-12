package com.example.rentalfilm;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryption {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public PasswordEncryption(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void updatePassword(String username, String newPassword) {
        String hashedPassword = passwordEncoder.encode(newPassword);
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        jdbcTemplate.update(sql, hashedPassword, username);
        System.out.println("Password berhasil diperbarui untuk: " + username);
    }
}
