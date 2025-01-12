package com.example.rentalfilm.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[] { email }, this::mapRowToUser);
            return user;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No user found");
            return null;
        }
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setEmail(rs.getString("email"));
        user.setNama(rs.getString("nama"));
        user.setPass(rs.getString("pass"));
        user.setRole(rs.getString("role"));

        return user;
    }

    @Override
    public void save(User user) {
        String role = "user";
        String sql = "INSERT INTO users (email, nama, pass, role) VALUES (?, ?,?,?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getNama(), user.getPass(), role);
    }
}
