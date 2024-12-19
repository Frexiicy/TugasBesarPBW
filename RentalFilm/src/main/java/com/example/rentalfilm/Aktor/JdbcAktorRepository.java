package com.example.rentalfilm.Aktor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcAktorRepository implements AktorRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Aktor> findAll() {
        String sql = "SELECT * FROM Aktor";
        return jdbcTemplate.query(sql, this::mapRowToAktor);
    }
    
    private Aktor mapRowToAktor (ResultSet rs, int rowNum) throws SQLException{
        Aktor aktor = new Aktor();
        aktor.setId(rs.getInt("id"));
        aktor.setNama(rs.getString("nama"));
        return aktor;
    }
}
