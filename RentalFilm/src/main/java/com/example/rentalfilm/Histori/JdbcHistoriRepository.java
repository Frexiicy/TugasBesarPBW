package com.example.rentalfilm.Histori;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rentalfilm.Laporan.Laporan;

@Repository
public class JdbcHistoriRepository implements HistoriRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // public Histori mapRowToHistori(ResultSet rs, int rowNum) throws SQLException {
    //     LocalDate tanggalKembali = rs.getDate("tanggal_kembali") != null ? rs.getDate("tanggal_kembali").toLocalDate()
    //             : null;

    //     String status = (tanggalKembali != null) ? "returned" : "borrowed";

    //     return new Histori(rs.getInt("idfilm"), rs.getString("emailu"), rs.getDate("tanggal_pinjam").toLocalDate(),
    //             tanggalKembali != null ? tanggalKembali : "-", rs.getString("judul"), rs.getDouble("harga"),
    //             rs.getDouble("denda"), rs.getString("nama"), status);
    // }

    public Histori mapRowToHistori(ResultSet rs, int rowNum) throws SQLException {
        String nama = rs.getString("nama");
        LocalDate tanggalKembali = rs.getDate("tanggal_kembali") != null ? rs.getDate("tanggal_kembali").toLocalDate() : null;
    
        System.out.println("Nama: " + nama);  // Tambahkan log untuk nama
        System.out.println("Tanggal Kembali: " + tanggalKembali);  // Cek tanggal kembali
    
        String status = (tanggalKembali != null) ? "returned" : "borrowed";
        System.out.println(status);
    
        return new Histori(rs.getInt("idfilm"), rs.getString("emailu"), rs.getDate("tanggal_pinjam").toLocalDate(),
                tanggalKembali != null ? tanggalKembali : "-", rs.getString("judul"), rs.getDouble("harga"),
                rs.getDouble("denda"), nama, status, rs.getInt("idPeminjaman"));
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
    @Override /* Checkout film dari keranjang */
    public void addToPeminjaman(String emailu, Integer id) {
        String sqlStok = "SELECT stok FROM film WHERE id = ?";
        Integer currStok = jdbcTemplate.queryForObject(sqlStok, Integer.class, id);

        if (currStok != null && currStok > 0) {
            String sqlPinjam = "INSERT INTO peminjaman (emailu, idfilm, tanggal) VALUES (?, ?, now())";
            jdbcTemplate.update(sqlPinjam, emailu, id);

            String sqlHapus = "DELETE FROM keranjang WHERE emailu = ? AND idfilm = ?";
            jdbcTemplate.update(sqlHapus, emailu, id);

            String sqlUpStok = "UPDATE film SET stok = stok - 1 WHERE id = ?";
            jdbcTemplate.update(sqlUpStok, id);
        } else {
            throw new RuntimeException("Unavailable stock.");
        }
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

    @Override
    public List<Laporan> getLaporan() {
        String sql = "SELECT judul, tanggal_pinjam, tanggal_kembali FROM histori";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String judul = rs.getString("judul");
            LocalDate tanggalSewa = rs.getDate("tanggal_pinjam").toLocalDate();
            LocalDate tanggalKembali = rs.getDate("tanggal_kembali") != null
                    ? rs.getDate("tanggal_kembali").toLocalDate()
                    : null;
            return new Laporan(judul, tanggalSewa, tanggalKembali);
        });
    }

    @Override
    public List<Histori> findAll() {
        String sql = "SELECT * FROM histori";
        return jdbcTemplate.query(sql, this::mapRowToHistori);
    }


    @Override
    public Histori findByIdPeminjaman(int idPeminjaman) {
        String sql = "SELECT * FROM histori WHERE idPeminjaman = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToHistori, idPeminjaman);
    }


    @Override
    public void addPengembalian(String email, int idFilm, int idPeminjaman, LocalDate tanggalPinjam, LocalDate tanggalKembali) {
        String sql = "INSERT INTO Pengembalian (emailU, idFilm, tanggal, denda, idPeminjaman) VALUES (?,?,?,?,?)";

        LocalDate dueDate = tanggalPinjam.plusDays(7);
        double totalDenda = 0;

        if (tanggalKembali.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            totalDenda = daysOverdue * 1000;
        }

        String dendaIdr = String.format("Rp %,.0f", totalDenda);

        jdbcTemplate.update(sql, email, idFilm, tanggalKembali, dendaIdr, idPeminjaman);
    }
}