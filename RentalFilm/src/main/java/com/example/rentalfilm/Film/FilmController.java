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
@RequestMapping("film")
public class FilmController {
    @Autowired
    private FilmRepository repo;

    @Autowired
    private AktorRepository aktorRepo;

    @GetMapping("/f")
    public String getInfoFilm(@RequestParam(value = "status", required = false) String status,
            @RequestParam("id") int id, Model model) {
        InfoFilm infoFilm = repo.getInfoFilmById(id);
        Integer rating = infoFilm.getRating() / 2;

        model.addAttribute("infofilm", infoFilm);
        model.addAttribute("rating", rating);
        model.addAttribute("status", status);
        return "Film/infofilm";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("idfilm") int idfilm, HttpSession session) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null) {
            boolean success = repo.addToCart(emailu, idfilm);
            if (success) {
                return "redirect:/f?status=success";
            } else {
                return "redirect:/f?status=failure";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/by-rating")
    @ResponseBody
    public List<Film> getFilmsByRating(@RequestParam(name = "rating", required = false) Integer rating) {
        if (rating == null) {
            // Menampilkan semua film jika tidak ada rating yang dipilih
            return repo.findAllFilms();
        }

        List<Film> films = repo.findFilmsByRating(rating);

        if (films.isEmpty()) {
            // Jika tidak ada film dengan rating tertentu, return pesan tidak ada film
            return new ArrayList<>();
        }

        return films;
    }

    @GetMapping("/film-slideshow")
    public List<Film> getAllFilmsForSlideshow() {
        return repo.findAllFilms();
    }

    // @GetMapping("/search")
    // public List<Film> searchFilmsByTitle(@RequestParam("title") String title) {
    //     List<Film> films = repo.findFilmsByTitle(title);
    //     return films;
    // }
        // Menambahkan pencarian berdasarkan judul
        @GetMapping("/search")
        @ResponseBody
        public List<Film> searchFilmsByTitle(@RequestParam("title") String title) {
            List<Film> films = repo.findFilmsByTitle(title);
            return films;
        }

    // @GetMapping("/search-by-age")
    // public List<Film> searchFilmsByAge(@RequestParam("age") String batasUsia) {
    //     return repo.findFilmsByAge(batasUsia);
    // }
    @GetMapping("/search-by-age")
    @ResponseBody
    public List<Film> searchFilmsByAge(@RequestParam("age") String batasUsia) {
        return repo.findFilmsByAge(batasUsia);
    }

    // @GetMapping("/search-by-genre")
    // @ResponseBody
    // public List<Film> searchFilmsByGenre(@RequestParam("genreId") int genreId) {
    //     return repo.findFilmsByGenre(genreId);
    // }
    @GetMapping("/search-by-genre")
    @ResponseBody
    public List<Film> searchFilmsByGenre(@RequestParam("genreId") int genreId) {
        return repo.findFilmsByGenre(genreId);
    }

    // @GetMapping("/search-by-actor")
    // public List<Film> searchFilmsByActor(@RequestParam("actorId") int actorId) {
    //     return repo.findFilmsByActor(actorId);
    // }
    @GetMapping("/search-by-actor")
    @ResponseBody
    public List<Film> searchFilmsByActor(@RequestParam("actorId") int actorId) {
        return repo.findFilmsByActor(actorId);
    }

    // @GetMapping("/search-by-actor-name")
    // @ResponseBody
    // public List<Film> searchFilmsByActorName(@RequestParam("actorName") String actorName) {
    //     int actorId = aktorRepo.findIdByNama(actorName);
    //     return repo.findFilmsByActor(actorId);
    // }
    @GetMapping("/search-by-actor-name")
    @ResponseBody
    public List<Film> searchFilmsByActorName(@RequestParam("actorName") String actorName) {
        int actorId = aktorRepo.findIdByName(actorName);
        return repo.findFilmsByActor(actorId);
    }
}
