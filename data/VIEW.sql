-- untuk menampilkan informasi film di html
CREATE OR REPLACE VIEW infoFilm AS
SELECT
	f.*, a.foto,
	STRING_AGG(DISTINCT g.nama, ', ') AS genre,
	STRING_AGG(DISTINCT a.nama, ', ') AS aktor
FROM
	Film f
JOIN
	GenreFilm gf ON f.id = gf.idfilm
JOIN
	Genre g ON gf.idgenre = g.id
JOIN
	AktorFilm af ON f.id = af.idfilm
JOIN
	Aktor a ON af.idaktor = a.id
GROUP BY
	f.id, a.foto;

-- untuk menampilkan histori satu user
CREATE OR REPLACE VIEW histori AS
SELECT 
	u.nama,
    p.emailu,
    p.idfilm,
    p.tanggal AS tanggal_pinjam,
    f.judul,
    f.harga,
    pg.tanggal AS tanggal_kembali,
    pg.denda
FROM 
    peminjaman p
LEFT JOIN 
    pengembalian pg ON p.id = pg.idpeminjaman
LEFT JOIN 
    film f ON p.idfilm = f.id
LEFT JOIN
	users u ON p.emailu = u.email;

-- untuk menampilkan cart suatu user
CREATE OR REPLACE VIEW cart AS
SELECT
	f.id AS idfilm, f.judul, f.harga, k.emailu
FROM
	Film f
JOIN
	Keranjang k ON f.id = k.idfilm;

-- untuk menampilkan histori peminjaman suatu user
CREATE OR REPLACE VIEW historipinjam AS
SELECT
	f.id AS idfilm, p.emailu, p.tanggal, f.judul
FROM
	Film f
JOIN
	Peminjaman p ON f.id = p.idfilm;

SELECT * FROM histori;

SELECT
	f.id AS idfilm, p.emailu, p.tanggal, f.judul
FROM
	Film f
JOIN
	Peminjaman p ON f.id = p.idfilm;


SELECT DISTINCT tanggal FROM Peminjaman ORDER BY tanggal;
SELECT COUNT(*) FROM Peminjaman GROUP BY tanggal ORDER BY tanggal;


SELECT judul, tanggal_pinjam, tanggal_kembali FROM histori;