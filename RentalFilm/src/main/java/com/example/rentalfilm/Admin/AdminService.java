package com.example.rentalfilm.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rentalfilm.Film.FilmRepository;
import com.example.rentalfilm.Histori.HistoriRepository;
import com.example.rentalfilm.Laporan.Laporan;

@Service
public class AdminService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private HistoriRepository repoHistori;

    // UBAH - Logika untuk mendapatkan data laporan grafik penyewaan
    public List<String> getGrafikLabels() {
        return filmRepository.getTanggalPeminjaman();
    }

    public List<Integer> getGrafikData() {
        return filmRepository.getJumlahPeminjaman();
    }

    public List<Laporan> getLaporanPeminjaman() {
        return repoHistori.getLaporan();
    }
}