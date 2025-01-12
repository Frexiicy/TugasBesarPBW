package com.example.rentalfilm.Film;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository {
    void saveFilm(Film film);

    void insertAktorFilm(int idFilm, int idAktor);

    void insertGenreFilm(int idFilm, int idGenre);

    Film findByJudulandRating(String judul, int rating);

    List<Film> findAllFilms();

    List<Film> findFilmsByRating(int rating);

    List<Film> findFilmsByTitle(String title);

    List<Film> findFilmsByAge(String batasUsia);

    List<Film> findFilmsByGenre(int genreId);

    List<Film> findFilmsByActor(int actorId);

    InfoFilm getInfoFilmById(int id);

    List<Film> getKeranjangByEmail(String emailu);

    boolean addToCart(String emailu, int idfilm);

    List<String> getTanggalPeminjaman();

    List<Integer> getJumlahPeminjaman();

    Film findFilmsById (int id);

    int updateFilm (Film film);
}