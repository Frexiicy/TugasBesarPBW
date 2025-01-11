package com.example.rentalfilm.Genre;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository {
    List<Genre> findAll();

    void saveFilmGenre(int filmId, int genreId);

    Genre findById(int id);

    void saveGenre(String nama);

    Genre findByNama(String nama);

    List<Genre> getAllGenres(); // Metode baru untuk mengambil semua genre
}