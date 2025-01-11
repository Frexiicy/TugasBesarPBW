package com.example.rentalfilm.Histori;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriService {

    @Autowired
    HistoriRepository repoHistori;

    private static final double dendaPerHari = 1000;

    public List<Histori> getPeminjaman(String emailu) {
        List<Histori> listPinjam = repoHistori.getPeminjamanByEmail(emailu);
        List<Histori> res = new ArrayList<>();

        for (Histori h : listPinjam) {
            LocalDate tanggalPinjam = h.getTanggalPinjam();
            LocalDate dueDate = tanggalPinjam.plusDays(7);
            double totalDenda = 0;

            if (LocalDate.now().isAfter(dueDate)) {
                long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                totalDenda = daysOverdue * dendaPerHari;
            }

            String dendaIdr = String.format("Rp %,.0f", totalDenda);

            res.add(new Histori(h.getIdfilm(), h.getEmailu(), tanggalPinjam, dueDate, h.getJudul(), h.getHarga(),
                    dendaIdr));
        }

        return res;
    }
}
