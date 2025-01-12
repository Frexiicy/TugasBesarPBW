package com.example.rentalfilm.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.rentalfilm.Aktor.Aktor;
import com.example.rentalfilm.Genre.Genre;

@Repository
public class JdbcFilmRepository implements FilmRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        return new Film(rs.getInt("id"), rs.getString("judul"), rs.getInt("rating"), rs.getString("sinopsis"),
                rs.getString("batas_usia"), rs.getInt("stok"), rs.getDouble("harga"));
    }

    public Film mapRowToCart(ResultSet rs, int rowNum) throws SQLException {
        return new Film(rs.getInt("id"), rs.getString("judul"), rs.getDouble("harga"));
    }

    @Override
    public List<Film> findAllFilms() {
        String sql = "SELECT * FROM Film ORDER BY id ASC";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }

    @Override
    public void saveFilm(Film film) {
        String sql = "INSERT INTO Film (judul, rating, sinopsis, batas_usia, stok, harga, poster) VALUES (?,?,?,?,?,?, ?)";
        jdbcTemplate.update(sql, film.getJudul(), film.getRating(), film.getSinopsis(), film.getBatas_usia(),
                film.getStok(), 30000, film.getPoster());
    }

    @Override
    public void insertAktorFilm(int idFilm, int idAktor) {
        String sql = "INSERT INTO aktorFilm (idFilm, idAktor) VALUES (?,?)";
        jdbcTemplate.update(sql, idFilm, idAktor);
    }

    @Override
    public void insertGenreFilm(int idFilm, int idGenre) {
        String sql = "INSERT INTO genreFilm (idFilm, idGenre) VALUES (?,?)";
        jdbcTemplate.update(sql, idFilm, idGenre);
    }

    @Override
    public Film findByJudulandRating(String judul, int rating) {
        String sql = "SELECT * FROM Film WHERE judul ILIKE ? AND rating = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm, "%" + judul + "%", rating);
    }

    public InfoFilm mapRowToInfoFilm(ResultSet rs, int rowNum) throws SQLException {
        String[] listGenre = rs.getString("genre").split(", ");
        List<Genre> genre = Arrays.stream(listGenre).map(nama -> new Genre(0, nama)).collect(Collectors.toList());

        String[] listAktor = rs.getString("aktor").split(", ");
        List<Aktor> aktor = Arrays.stream(listAktor).map(nama -> {
            try {
                int id = rs.getInt("id");
                byte[] foto = rs.getBytes("foto");
                return new Aktor(id, nama, foto);
            } catch (SQLException e) {
                throw new RuntimeException("Error mapping Aktor", e);
            }
        }).collect(Collectors.toList());

        return new InfoFilm(rs.getInt("id"), rs.getString("judul"), rs.getInt("rating"), rs.getString("sinopsis"),
                rs.getString("batas_usia"), rs.getInt("stok"), rs.getDouble("harga"), genre, aktor);
    }

    @Override
    public InfoFilm getInfoFilmById(int id) {
        String sql = "SELECT * FROM infofilm WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, this::mapRowToInfoFilm);
    }

    @Override
    public boolean addToCart(String emailu, int idfilm) {
        String sql = "INSERT INTO keranjang (emailu, idfilm) VALUES (?, ?)";
        int rowsAffected = 0;
        try {
            rowsAffected = jdbcTemplate.update(sql, emailu, idfilm);
            return rowsAffected > 0;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public List<Film> getKeranjangByEmail(String emailu) {
        String sql = "SELECT * FROM cart WHERE emailu = ?";
        return jdbcTemplate.query(sql, this::mapRowToCart, emailu);
    }

    @Override
    public List<Film> findFilmsByRating(int rating) {
        String sql = "SELECT * FROM Film WHERE rating = ? ORDER BY rating DESC";
        return jdbcTemplate.query(sql, this::mapRowToFilm, rating);
    }

    @Override
    public List<Film> findFilmsByTitle(String title) {
        String sql = "SELECT * FROM Film WHERE REPLACE(LOWER(judul), ' ', '') LIKE LOWER(REPLACE(?, ' ', '')) ORDER BY judul ASC";
        return jdbcTemplate.query(sql, this::mapRowToFilm, "%" + title + "%");
    }

    @Override
    public List<Film> findFilmsByAge(String batasUsia) {
        String sql = "SELECT * FROM Film WHERE LOWER(batas_usia) = LOWER(?) ORDER BY judul ASC";
        return jdbcTemplate.query(sql, this::mapRowToFilm, batasUsia);
    }

    @Override
    public List<Film> findFilmsByGenre(int genreId) {
        String sql = "SELECT f.* FROM Film f JOIN GenreFilm gf ON f.id = gf.idFilm WHERE gf.idGenre = ? ORDER BY f.judul ASC";
        return jdbcTemplate.query(sql, this::mapRowToFilm, genreId);
    }

    @Override
    public List<Film> findFilmsByActor(int actorId) {
        String sql = "SELECT f.* FROM Film f JOIN AktorFilm fa ON f.id = fa.idFilm WHERE fa.idAktor = ? ORDER BY f.judul ASC";
        return jdbcTemplate.query(sql, this::mapRowToFilm, actorId);
    }

    @Override
    public List<String> getTanggalPeminjaman() {
        // Ambil data tanggal mentah dari database
        String sql = "SELECT DISTINCT tanggal FROM Peminjaman ORDER BY tanggal";
        List<String> tanggalList = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> rs.getDate("tanggal").toLocalDate().toString()
        );

        return tanggalList;
    }

    @Override
    public List<Integer> getJumlahPeminjaman() {
        // Ambil jumlah peminjaman berdasarkan tanggal
        String sql = "SELECT COUNT(*) FROM Peminjaman GROUP BY tanggal ORDER BY tanggal";
        return jdbcTemplate.queryForList(sql, Integer.class);
    }
}
