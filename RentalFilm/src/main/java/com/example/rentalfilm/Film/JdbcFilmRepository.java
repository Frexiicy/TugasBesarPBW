package com.example.rentalfilm.Film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcFilmRepository implements FilmRepository {
     @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveFilm(Film film) {
        String sql = "INSERT INTO Film (judul, rating, sinopsis, batas_usia, stok, harga) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, film.getJudul(), film.getRating(), film.getSinopsis(), film.getBatas_usia(), film.getStok(), film.getHarga());
    }
    
}
