package com.example.rentalfilm.Entity;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    private String judul;
    private int rating;
    private String sinopsis;
    private String batasUsia;
    private int stok;
    private double harga;
}
