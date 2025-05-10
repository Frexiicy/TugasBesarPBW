package com.example.rentalfilm.Laporan;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Laporan {
    private String judul;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
}
