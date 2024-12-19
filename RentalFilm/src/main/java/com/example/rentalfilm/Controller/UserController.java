package com.example.rentalfilm.Controller;

import com.example.rentalfilm.Entity.Film;
import com.example.rentalfilm.Repository.FilmRepository;
import com.example.rentalfilm.Service.FilmService;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class UserController {
    
    @Autowired
    private FilmService filmService;

    @GetMapping("/")
    public String redirectToLogin() {
        return "user/homeUser";
    }

    //bagian homescreen dari user
    // @GetMapping("/homeUser")
    // public String showHomeUser(Model model, HttpSession session){
    //     return "user/homeUser";
    // }

    @GetMapping("/homeUser")
    public String showHomeUser(Model model, HttpSession session) {
        List<Film> films = filmService.getAllFilms();
        model.addAttribute("films", films);
        return "user/homeUser";
    }


    //bagian pindah ke halaman advanced search
    // @GetMapping("/search")
    // public String showAdvancedSearch(Model model, HttpSession session){
    //     return "user/search";
    // }
    
    @GetMapping("/search")
    public String searchFilms(@RequestParam("title") String title, Model model) {
        List<Film> films = filmService.searchFilms(title);
        model.addAttribute("films", films);
        return "user/search";
    }

}
