package com.example.rentalfilm.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcGenreRepository implements GenreRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findALl() {
        String sql = "SELECT * FROM Genre";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }
    
    private Genre mapRowToGenre (ResultSet rs, int rowNum) throws SQLException{
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setNama(rs.getString("nama"));
        return genre;
    }
}
