package com.example.rentalfilm.Repository;

import com.example.rentalfilm.Entity.Film;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ImplFilmRepository implements FilmRepository {

    private final List<Film> films = new ArrayList<>();

    public ImplFilmRepository() {
        // Dummy data
        films.add(new Film(1, "Oppenheimer", 9, "A historical drama", "PG-13", 10, 50000));
        films.add(new Film(2, "Mission Impossible", 8, "Action thriller", "PG-13", 5, 45000));
        films.add(new Film(3, "Fast & Furious 9", 7, "Action packed", "PG-13", 8, 40000));
    }

    @Override
    public List<Film> findAll() {
        return films;
    }

    @Override
    public Film findById(int id) {
        return films.stream().filter(film -> film.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Film> findByTitle(String title) {
        return films.stream()
                .filter(film -> film.getJudul().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Film film) {
        films.add(film);
    }

    @Override
    public void delete(int id) {
        films.removeIf(film -> film.getId() == id);
    }
}
