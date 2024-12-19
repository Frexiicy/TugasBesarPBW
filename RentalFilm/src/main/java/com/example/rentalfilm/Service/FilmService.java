package com.example.rentalfilm.Service;

import com.example.rentalfilm.Entity.Film;
import com.example.rentalfilm.Repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(int id) {
        return filmRepository.findById(id);
    }

    public List<Film> searchFilms(String title) {
        return filmRepository.findByTitle(title);
    }
}
