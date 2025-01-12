package com.example.rentalfilm.Aktor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.rentalfilm.Genre.Genre;

@Repository
public class JdbcAktorRepository implements AktorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Aktor> findAll() {
        String sql = "SELECT * FROM Aktor";
        return jdbcTemplate.query(sql, this::mapRowToAktor);
    }

    private Aktor mapRowToAktor(ResultSet rs, int rowNum) throws SQLException {
        Aktor aktor = new Aktor(rs.getInt("id"), rs.getString("nama"), rs.getBytes("foto"));
        return aktor;
    }

    @Override
    public void saveFilmActor(int filmId, int aktorId) {
        String sql = "INSERT INTO aktorfilm (idfilm, idaktor) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, aktorId);
    }

    @Override
    public Aktor findByNama(String nama) {
        String sql = "SELECT * FROM aktor WHERE nama ILIKE ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToAktor, "%" + nama + "%");
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Aktor> findAllWithPagination(int page, int size) {
        String sql = "SELECT * FROM Aktor LIMIT ? OFFSET ?";
        int offset = page * size;
        return jdbcTemplate.query(sql, this::mapRowToAktor, size, offset);
    }

    @Override
    public void saveAktor(String nama, byte[] foto) {
        String sql = "INSERT INTO aktor (nama, foto) VALUES (?,?)";
        jdbcTemplate.update(sql, nama, foto);
    }

    @Override
    public int findIdByNama(String nama) {
        String sql = "SELECT id FROM Aktor WHERE LOWER(nama) = LOWER(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, nama);
    }
}
