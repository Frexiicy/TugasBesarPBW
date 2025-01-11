package com.example.rentalfilm.Aktor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Aktor {
    private int id;
    private String nama;
    private byte[] foto;
}
