package com.example.rentalfilm.Histori;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rentalfilm.Aktor.Aktor;
import com.example.rentalfilm.Genre.Genre;

@Repository
public class JdbcHistoriRepository implements HistoriRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Histori mapRowToHistori(ResultSet rs, int rowNum) throws SQLException {
        return new Histori(rs.getInt("idfilm"), rs.getString("emailu"), rs.getDate("tanggal_pinjam").toLocalDate(),
                rs.getDate("tanggal_kembali").toLocalDate(), rs.getString("judul"), rs.getDouble("harga"),
                rs.getDouble("denda"));
    }

    public Histori mapRowToPeminjaman(ResultSet rs, int rowNum) throws SQLException {
        return new Histori(rs.getInt("idfilm"), rs.getString("emailu"), rs.getDate("tanggal").toLocalDate(),
                rs.getString("judul"));
    }

    @Override
    public List<Histori> getHistoribyEmail(String emailu) {
        String sql = "SELECT * FROM histori WHERE emailu = ?";
        return jdbcTemplate.query(sql, this::mapRowToHistori, emailu);
    }

    @Transactional
    @Override
    public void addToPeminjaman(String emailu, Integer id) {
        String sqlPinjam = "INSERT INTO peminjaman (emailu, idfilm, tanggal) VALUES (?, ?, now())";
        String sqlHapus = "DELETE FROM keranjang WHERE emailu = ? AND idfilm = ?";
        jdbcTemplate.update(sqlPinjam, emailu, id);
        jdbcTemplate.update(sqlHapus, emailu, id);
    }

    @Transactional
    @Override
    public void addToPengembalian(String emailu, Integer id) {
        String sqlPinjam = "SELECT tanggal FROM peminjaman WHERE emailu = ? AND idfilm = ?";
        LocalDate tanggalPinjam = jdbcTemplate.queryForObject(sqlPinjam, LocalDate.class, emailu, id);

        double denda = 0;
        LocalDate tanggalKembali = LocalDate.now();
        if (tanggalKembali.isAfter(tanggalPinjam.plusDays(7))) {
            long daysOverdue = ChronoUnit.DAYS.between(tanggalPinjam.plusDays(7), tanggalKembali);
            denda = daysOverdue * 1000;
        }

        String sqlKembali = "INSERT INTO pengembalian (emailu, idfilm, tanggal, denda) VALUES (?, ?, now(), ?)";
        String sqlHapus = "DELETE FROM peminjaman WHERE emailu = ? AND idfilm = ?";

        jdbcTemplate.update(sqlKembali, emailu, id, denda);
        jdbcTemplate.update(sqlHapus, emailu, id);
    }

    @Override
    public List<Histori> getPeminjamanByEmail(String emailu) {
        String sql = "SELECT * FROM historipinjam WHERE emailu = ?";
        List<Histori> listPinjam = jdbcTemplate.query(sql, new Object[] { emailu }, this::mapRowToPeminjaman);
        for (Histori h : listPinjam) {
            LocalDate tanggalPinjam = h.getTanggalPinjam();
            LocalDate dueDate = tanggalPinjam.plusDays(7);
            double totalDenda = 0;

            if (LocalDate.now().isAfter(dueDate)) {
                long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                totalDenda = daysOverdue * 1000;
            }

            String dendaIdr = String.format("Rp %,.0f", totalDenda);

            h.setDenda(dendaIdr);
        }

        return listPinjam;
    }
}