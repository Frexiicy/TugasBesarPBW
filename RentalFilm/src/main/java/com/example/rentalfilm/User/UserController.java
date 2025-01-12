package com.example.rentalfilm.User;

import com.example.rentalfilm.User.User;
import com.example.rentalfilm.User.JdbcUserRepository;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rentalfilm.Film.Film;
import com.example.rentalfilm.Film.FilmRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class UserController {
    @GetMapping("/homeUser")
    public String homeUser() {
        return "User/homeUser"; // Path dari file HTML di folder templates/User
    }

    // bagian pindah ke halaman advanced search
    @GetMapping("/searchUser")
    public String showAdvancedSearch(Model model, HttpSession session) {
        return "User/search";
    }
}