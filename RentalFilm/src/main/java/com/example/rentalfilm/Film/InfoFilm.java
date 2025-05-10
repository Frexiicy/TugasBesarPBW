package com.example.rentalfilm.Film;

import java.util.List;

import com.example.rentalfilm.Aktor.Aktor;
import com.example.rentalfilm.Genre.Genre;

import lombok.Data;

@Data
public class InfoFilm {
    private int id;
    private String judul;
    private int rating;
    private String sinopsis;
    private String batas_usia;
    private int stok;
    private String harga;
    private List<Genre> genre;
    private List<Aktor> aktor;

    public InfoFilm(int id, String judul, int rating, String sinopsis, String batas_usia, int stok, double harga,
            List<Genre> genre, List<Aktor> aktor) {
        this.id = id;
        this.judul = judul;
        this.rating = rating;
        this.sinopsis = sinopsis;
        this.batas_usia = batas_usia;
        this.stok = stok;
        this.harga = convertToIdr(harga);
        this.genre = genre;
        this.aktor = aktor;
    }

    private String convertToIdr(double harga) {
        return String.format("Rp %,.0f", harga);
    }
}