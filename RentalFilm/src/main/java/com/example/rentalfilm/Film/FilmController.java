package com.example.rentalfilm.Film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tambahFilm")
public class FilmController {
    @Autowired
    private FilmRepository repo;

    public FilmController(FilmRepository repo){
        this.repo = repo;
    }

    @GetMapping()
    public String tambahFilm(){
        return "tambahFilm";
    }

}
