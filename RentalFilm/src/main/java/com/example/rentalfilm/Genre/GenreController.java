package com.example.rentalfilm.Genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public String getSearchPage(Model model) {
        List<Genre> genres = genreRepository.findAll();
        System.out.println("Genres retrieved: " + genres); // Debug log
        model.addAttribute("genres", genres); // Menambahkan genre ke model
        return "search"; // Mengarahkan ke search.html
    }

    @GetMapping("/all")
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}