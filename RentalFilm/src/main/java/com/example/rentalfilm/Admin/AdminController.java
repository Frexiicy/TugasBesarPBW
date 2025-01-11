package com.example.rentalfilm.Admin;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rentalfilm.Aktor.AktorRepository;
import com.example.rentalfilm.Film.Film;
import com.example.rentalfilm.Film.FilmRepository;
import com.example.rentalfilm.Genre.GenreRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class AdminController {
    @Autowired
    private FilmRepository repo;
    private AktorRepository aktorRepo;
    private GenreRepository genreRepo;

    @Autowired
    public AdminController(FilmRepository repo, AktorRepository aktorRepo, GenreRepository genreRepo) {
        this.repo = repo;
        this.aktorRepo = aktorRepo;
        this.genreRepo = genreRepo;
    }

    @GetMapping("/homeAdmin")
    public String homeAdmin(Model model) {
        return "Admin/homeAdmin";
    }

    @GetMapping("/searchAdmin")
    public String showAdvancedSearchAdmin(Model model, HttpSession session) {
        return "Admin/searchAdmin";
    }

    @GetMapping("/addFilm")
    public String showAddFilmForm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("actors", aktorRepo.findAll());
        model.addAttribute("genres", genreRepo.findAll());
        return "Admin/tambahFilm";
    }

    @PostMapping("/addFilm")
    public String saveFilm(
            @RequestParam("posterBase64") String posterBase64,
            @RequestParam("judul") String judul,
            @RequestParam("rating") int rating,
            @RequestParam("aktor") List<String> aktors,
            @RequestParam("genres") List<Integer> genres,
            @RequestParam("sinopsis") String sinopsis,
            @RequestParam("batas_usia") String batasUsia,
            @RequestParam("stok") int stok) {
        Film film = new Film();
        film.setJudul(judul);
        film.setRating(rating);
        film.setSinopsis(sinopsis);
        film.setBatas_usia(batasUsia);
        film.setStok(stok);

        // Proses poster base64
        if (posterBase64 != null && !posterBase64.isEmpty()) {
            film.setPoster(Base64.getDecoder().decode(posterBase64)); // Simpan sebagai byte[]
        }

        repo.saveFilm(film);

        Film saved = repo.findByJudulandRating(judul, rating);

        int idFilm = saved.getId();
        System.out.println(idFilm);

        if (aktors != null) {
            for (String namaAktor : aktors) {
                int idAktor = aktorRepo.findByNama(namaAktor).getId();
                repo.insertAktorFilm(idFilm, idAktor);
            }
        }

        if (genres != null) {
            for (int idGenre : genres) {
                repo.insertGenreFilm(idFilm, idGenre);
            }
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/addActor")
    public String showAddAktorForm() {
        return "Admin/tambahAktor";
    }

    @PostMapping("/addActor")
    public String saveAktor(
            @RequestParam("nama") String nama,
            @RequestParam("fotoBase64") String fotoBase64,
            Model model) {

        if (aktorRepo.findByNama(nama) != null) {
            model.addAttribute("errorMessage", "Aktor dengan nama '" + nama + "' sudah ada.");
            return "Admin/tambahAktor";
        }

        byte[] fotoBytes = Base64.getDecoder().decode(fotoBase64);
        aktorRepo.saveAktor(nama, fotoBytes);

        return "redirect:/dashboard";
    }

    @GetMapping("/addGenre")
    public String showAddGenreForm() {
        return "Admin/tambahGenre";
    }

    @PostMapping("/addGenre")
    public String saveGenre(
            @RequestParam("nama") String nama,
            Model model) {

        if (genreRepo.findByNama(nama) != null) {
            model.addAttribute("errorMessage", "Genre sudah ada.");
            return "Admin/tambahGenre";
        }

        genreRepo.saveGenre(nama);

        return "redirect:/dashboard";
    }

}