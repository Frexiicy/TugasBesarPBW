package com.example.rentalfilm.Film;

import java.util.List;

import com.example.rentalfilm.Aktor.Aktor;
import com.example.rentalfilm.Genre.Genre;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    private String judul;
    private int rating;
    private String sinopsis;
    private String batas_usia;
    private int stok;
    private String harga;
    private byte[] poster;
    private List<Genre> genres;
    private List<Aktor> actors;
    private String posterBase64;
    private List<Integer> genresId;

    public Film(int id, String judul, int rating, String sinopsis, String batas_usia, int stok, double harga,
            byte[] poster) {
        this.id = id;
        this.judul = judul;
        this.rating = rating;
        this.sinopsis = sinopsis;
        this.batas_usia = batas_usia;
        this.stok = stok;
        this.harga = convertToIdr(harga);
        this.poster = poster;
    }

    public Film(int id, String judul, double harga) {
        this.id = id;
        this.judul = judul;
        this.harga = convertToIdr(harga);
    }

    private String convertToIdr(double harga) {
        return String.format("Rp %,.0f", harga);
    }

    public Film() {

    }
}