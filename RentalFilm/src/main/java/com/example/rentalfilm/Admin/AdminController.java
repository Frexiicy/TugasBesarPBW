package com.example.rentalfilm.Admin;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rentalfilm.Aktor.AktorRepository;
import com.example.rentalfilm.Film.Film;
import com.example.rentalfilm.Film.FilmRepository;
import com.example.rentalfilm.Genre.Genre;
import com.example.rentalfilm.Genre.GenreRepository;
import com.example.rentalfilm.Histori.Histori;
import com.example.rentalfilm.Histori.HistoriRepository;
import com.example.rentalfilm.Laporan.Laporan;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/a")
public class AdminController {
    @Autowired
    private FilmRepository repo;
    private AktorRepository aktorRepo;
    private GenreRepository genreRepo;
    private HistoriRepository repoHistori;

    @Autowired
    public AdminController(FilmRepository repo, AktorRepository aktorRepo, GenreRepository genreRepo, HistoriRepository repoHistori) {
        this.repo = repo;
        this.aktorRepo = aktorRepo;
        this.genreRepo = genreRepo;
        this.repoHistori = repoHistori;
    }

    @Autowired
    private AdminService adminService;

    @GetMapping("/home")
    public String homeAdmin(Model model) {
        return "Admin/homeAdmin";
    }

    @GetMapping("/search")
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

        return "redirect:/a/dashboard";
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

        return "redirect:/a/dashboard";
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

        return "redirect:/a/dashboard";
    }

    @GetMapping("/laporan")
    public String showGrafikLaporan(Model model) {
        List<String> labels = adminService.getGrafikLabels();
        List<Integer> data = adminService.getGrafikData();
        List<Laporan> laporanPeminjaman = adminService.getLaporanPeminjaman();

        model.addAttribute("grafikLabels", labels);
        model.addAttribute("grafikData", data);
        model.addAttribute("laporanPeminjaman", laporanPeminjaman);
        return "Admin/laporan"; // View khusus admin untuk laporan
    }

    @GetMapping("/listFilm")
    public String listFilm(Model model) {
        List<Film> films = repo.findAllFilms();

        films.forEach(film -> {
            if (film.getPoster() != null) {
                String base64Poster = Base64.getEncoder().encodeToString(film.getPoster());
                film.setPosterBase64("data:image/jpg;base64," + base64Poster);
            } else {
                film.setPosterBase64("https://via.placeholder.com/150");
            }
        });

        model.addAttribute("films", films);
        return "Admin/listFilm"; 
    }

    @GetMapping("/editFilm/{id}")
    public String editFilm(@PathVariable("id") int id, Model model) {
        Film film = repo.findFilmsById(id);

        if (film != null) {
            List<Genre> genres = genreRepo.findAll();
            model.addAttribute("film", film);
            model.addAttribute("genres", genres);
            model.addAttribute("actors", aktorRepo.findAll());
            return "Admin/editFilm"; 
        } else {
           
            model.addAttribute("errorMessage", "Film not found!");
            return "redirect:/listFilm";
        }
    }

    @PostMapping("/editFilm")
    public String updateFilm(@RequestParam(value = "id", required = true) int id,
                            @RequestParam("judul") String judul,
                            @RequestParam("sinopsis") String sinopsis,
                            @RequestParam("stok") int stok,
                            @RequestParam("batas_usia") String batasUsia,
                            @RequestParam(value = "posterBase64", required = false) String posterBase64,
                            Model model) {

        Film existingFilm = repo.findFilmsById(id);

        if (existingFilm != null) {
            existingFilm.setJudul(judul);
            existingFilm.setSinopsis(sinopsis);
            existingFilm.setStok(stok);
            existingFilm.setBatas_usia(batasUsia);

            if (posterBase64 != null && !posterBase64.isEmpty()) {
                existingFilm.setPoster(Base64.getDecoder().decode(posterBase64));
            }

            repo.updateFilm(existingFilm);
        } else {
            model.addAttribute("errorMessage", "Film not found!");
        }

        return "redirect:/listFilm"; 
    }

    @GetMapping("/h")
    public String getHistori(HttpSession session, Model model) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null) {
            List<Histori> histori = repoHistori.findAll();

            model.addAttribute("histori", histori);
            return "Admin/history";
        }

        return "redirect:/login";
    }

    @PostMapping("/returnFilm")
    @ResponseBody
    public Map<String, Object> returnFilm(@RequestBody Map<String, Object> requestData) {
        int idPeminjaman = Integer.parseInt((String) requestData.get("idPeminjaman"));
        LocalDate tanggalKembali = LocalDate.parse((String) requestData.get("tanggalKembali"));
    
        Histori cur = repoHistori.findByIdPeminjaman(idPeminjaman);
        
        repoHistori.addPengembalian(cur.getEmailu(), cur.getIdfilm(), cur.getIdPeminjaman(), cur.getTanggalPinjam(), tanggalKembali);
    
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }
    

}