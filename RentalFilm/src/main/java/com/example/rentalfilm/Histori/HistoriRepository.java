package com.example.rentalfilm.Histori;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.example.rentalfilm.Laporan.Laporan;

@Repository
public interface HistoriRepository {
    List<Histori> getHistoribyEmail(String emailu);

    List<Histori> getPeminjamanByEmail(String emailu);

    void addToPeminjaman(String emailu, Integer id);

    void addToPengembalian(String emailu, Integer id);

    List<Laporan> getLaporan();

    List<Histori> findAll();

    Histori findByIdPeminjaman (int idPeminjaman);

    void addPengembalian (String email, int idFilm, int idPeminjaman, LocalDate tanggalPinjam, LocalDate tanggalKembali);
}