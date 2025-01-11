package com.example.rentalfilm.Histori;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriRepository {
    List<Histori> getHistoribyEmail(String emailu);

    List<Histori> getPeminjamanByEmail(String emailu);

    void addToPeminjaman(String emailu, Integer id);

    void addToPengembalian(String emailu, Integer id);
}