package com.example.rentalfilm.Repository;

import com.example.rentalfilm.Entity.Film;
import java.util.List;

public interface FilmRepository {
    List<Film> findAll();
    Film findById(int id);
    List<Film> findByTitle(String title);
    void save(Film film);
    void delete(int id);
}
