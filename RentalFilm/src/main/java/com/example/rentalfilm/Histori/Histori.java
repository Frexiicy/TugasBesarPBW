package com.example.rentalfilm.Histori;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Histori {
    private int idPeminjaman;
    private int idfilm;
    private String emailu;
    private LocalDate tanggalPinjam;
    private Object tanggalKembali;
    private String judul;
    private String harga;
    private String denda;
    private String nama;
    private String status;

    public Histori(int idfilm, String emailu, LocalDate tanggalPinjam, Object tanggalKembali, String judul,
            double harga, double denda, String nama, String status, int idPeminjaman) {
        this.idfilm = idfilm;
        this.emailu = emailu;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali instanceof LocalDate ? (LocalDate) tanggalKembali
                : (String) tanggalKembali;
        this.judul = judul;
        this.harga = convertToIdr(harga);
        this.denda = convertToIdr(denda);
        this.nama = nama;
        this.status = status;
        this.idPeminjaman = idPeminjaman;
    }

    public Histori(int idfilm, String emailu, LocalDate tanggalPinjam, LocalDate tanggalKembali, String judul,
            String harga, String denda) {
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