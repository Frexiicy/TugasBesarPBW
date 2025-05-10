package com.example.rentalfilm.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("id"), rs.getString("nama"));
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM Genre";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    @Override
    public void saveFilmGenre(int filmId, int genreId) {
        String sql = "INSERT INTO genrefilm (idfilm, idgenre) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, genreId);
    }

    @Override
    public Genre findById(int id) {
        String sql = "SELECT * FROM genre WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, id);
    }

    @Override
    public void saveGenre(String nama) {
        String sql = "INSERT INTO genre (nama) VALUES (?)";
        jdbcTemplate.update(sql, nama);
    }

    @Override
    public Genre findByNama(String nama) {
        String sql = "SELECT * FROM genre WHERE nama ILIKE ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, "%" + nama + "%");
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
