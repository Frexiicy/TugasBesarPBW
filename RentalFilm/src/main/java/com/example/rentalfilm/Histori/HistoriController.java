package com.example.rentalfilm.Histori;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rentalfilm.Film.Film;
import com.example.rentalfilm.Film.FilmRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HistoriController {
    @Autowired
    private HistoriRepository repoHistori;

    @Autowired
    private FilmRepository repoFilm;

    @GetMapping("/h")
    public String getHistori(HttpSession session, Model model) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null) {
            List<Histori> histori = repoHistori.getHistoribyEmail(emailu);

            model.addAttribute("histori", histori);
            return "Histori/history";
        }

        return "redirect:/login";
    }

    @GetMapping("/c")
    public String getKeranjang(HttpSession session, Model model) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null) {
            List<Film> keranjang = repoFilm.getKeranjangByEmail(emailu);

            model.addAttribute("keranjang", keranjang);
            return "Histori/cart";
        }

        return "redirect:/login";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam(value = "selectedFilm", required = false) List<Integer> idfilm,
            HttpSession session, Model model) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null && idfilm != null && !idfilm.isEmpty()) {
            for (Integer id : idfilm) {
                repoHistori.addToPeminjaman(emailu, id);
            }
            return "redirect:/p";
        }

        return "redirect:/login";
    }

    @GetMapping("/p")
    public String getPeminjaman(HttpSession session, Model model) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null) {
            List<Histori> peminjaman = repoHistori.getPeminjamanByEmail(emailu);

            model.addAttribute("pinjam", peminjaman);
            return "Histori/history-detail";
        }

        return "redirect:/login";
    }

    @PostMapping("/return")
    public String returnPeminjaman(@RequestParam("idfilm") Integer idfilm, HttpSession session, Model model) {
        String emailu = (String) session.getAttribute("email");
        if (emailu != null) {
            repoHistori.addToPengembalian(emailu, idfilm);
            return "redirect:/h";
        }

        return "redirect:/login";
    }
}
