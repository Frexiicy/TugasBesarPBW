package com.example.rentalfilm.Film;

import lombok.Data;

@Data
public class Film{
    private int id;
    private String judul;
    private int rating;
    private String sinopsis;
    private String batas_usia;
    private int stok;
    private double harga;
}