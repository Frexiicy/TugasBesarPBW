package com.example.rentalfilm.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rentalfilm.Film.FilmRepository;

@Service
public class AdminService {
    @Autowired
    private FilmRepository filmRepository;

    //UBAH - Logika untuk mendapatkan data laporan grafik penyewaan
    public List<String> getGrafikLabels() {
        return filmRepository.getTanggalPeminjaman();
    }

    public List<Integer> getGrafikData() {
        return filmRepository.getJumlahPeminjaman();
    }
}
