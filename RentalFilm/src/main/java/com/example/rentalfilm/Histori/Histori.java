package com.example.rentalfilm.Histori;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Histori {
    private int idfilm;
    private String emailu;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String judul;
    private String harga;
    private String denda;

    public Histori(int idfilm, String emailu, LocalDate tanggalPinjam, LocalDate tanggalKembali, String judul,
            double harga,
            double denda) {
        this.idfilm = idfilm;
        this.emailu = emailu;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.judul = judul;
        this.harga = convertToIdr(harga);
        this.denda = convertToIdr(denda);
    }

    public Histori(int idfilm, String emailu, LocalDate tanggalPinjam, LocalDate tanggalKembali, String judul,
            String harga,
            String denda) {
        this.idfilm = idfilm;
        this.emailu = emailu;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.judul = judul;
        this.harga = harga;
        this.denda = denda;
    }

    public Histori(int idfilm, String emailu, LocalDate tanggalPinjam, String judul) {
        this.idfilm = idfilm;
        this.emailu = emailu;
        this.tanggalPinjam = tanggalPinjam;
        this.judul = judul;
    }

    private String convertToIdr(double harga) {
        return String.format("Rp %,.0f", harga);
    }
}