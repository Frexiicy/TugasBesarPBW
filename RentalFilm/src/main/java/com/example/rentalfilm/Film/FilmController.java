package com.example.rentalfilm.Film;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rentalfilm.Aktor.AktorRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/film")
public class FilmController {
    @Autowired
    private FilmRepository repoFilm;

    @Autowired
    private AktorRepository repoAktor;

    @GetMapping("/detail")
    public String getInfoFilm(@RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "id") int id, Model model) {
        InfoFilm infoFilm = repoFilm.getInfoFilmById(id);

        model.addAttribute("infofilm", infoFilm);
        model.addAttribute("status", status);
        return "Film/infofilm";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("idfilm") int idfilm, HttpSession session) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null) {
            boolean success = repoFilm.addToCart(emailu, idfilm);
            String status = success ? "success" : "failure";
            return "redirect:/film/detail?id=" + idfilm + "&status=" + status;
        }
        return "redirect:/login";
    }

    @GetMapping("/by-rating")
    @ResponseBody
    public List<Film> getFilmsByRating(@RequestParam(name = "rating", required = false) Integer rating) {
        System.out.println("Rating received: " + rating);
        if (rating == null || rating < 1 || rating > 5) {
            // Menampilkan semua film jika tidak ada rating yang dipilih
            return repoFilm.findAllFilms();
        }

        List<Film> films = repoFilm.findFilmsByRating(rating);

        if (films.isEmpty()) {
            // Jika tidak ada film dengan rating tertentu, return pesan tidak ada film
            return new ArrayList<>();
        }

        return films;
    }

    @GetMapping("/film-slideshow")
    @ResponseBody
    public List<Film> getAllFilmsForSlideshow() {
        return repoFilm.findAllFilms();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Film> searchFilmsByTitle(@RequestParam("title") String title) {
        List<Film> films = repoFilm.findFilmsByTitle(title);
        return films;
    }

    @GetMapping("/search-by-age")
    @ResponseBody
    public List<Film> searchFilmsByAge(@RequestParam("age") String batasUsia) {
        return repoFilm.findFilmsByAge(batasUsia);
    }

    @GetMapping("/search-by-genre")
    @ResponseBody
    public List<Film> searchFilmsByGenre(@RequestParam("genreId") int genreId) {
        return repoFilm.findFilmsByGenre(genreId);
    }

    @GetMapping("/search-by-actor")
    @ResponseBody
    public List<Film> searchFilmsByActor(@RequestParam("actorId") int actorId) {
        return repoFilm.findFilmsByActor(actorId);
    }

    @GetMapping("/search-by-actor-name")
    @ResponseBody
    public List<Film> searchFilmsByActorName(@RequestParam("actorName") String actorName) {
        int actorId = repoAktor.findIdByNama(actorName);
        return repoFilm.findFilmsByActor(actorId);
    }
}
