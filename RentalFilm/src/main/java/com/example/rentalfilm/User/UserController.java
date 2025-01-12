package com.example.rentalfilm.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.rentalfilm.Genre.Genre;
import com.example.rentalfilm.Genre.GenreRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/u")
public class UserController {

    @Autowired
    private GenreRepository repoGenre;

    @GetMapping("/home")
    public String homeUser() {
        return "User/homeUser"; // Path dari file HTML di folder templates/User
    }

    // bagian pindah ke halaman advanced search
    @GetMapping("/search")
    public String showAdvancedSearch(Model model, HttpSession session) {
        List<Genre> genre = repoGenre.findAll();
        model.addAttribute("genre", genre);
        return "User/search";
    }
}