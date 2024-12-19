DROP TABLE IF EXISTS AktorFilm CASCADE;
DROP TABLE IF EXISTS GenreFilm CASCADE;
DROP TABLE IF EXISTS Pengembalian CASCADE;
DROP TABLE IF EXISTS Peminjaman CASCADE;
DROP TABLE IF EXISTS Keranjang CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Genre CASCADE;
DROP TABLE IF EXISTS Aktor CASCADE;
DROP TABLE IF EXISTS Film CASCADE;

CREATE TABLE Film(
	id SERIAL PRIMARY KEY,
	judul varchar (50),
	rating int,
	sinopsis varchar (1000),
	batas_usia varchar(15),
	stok int,
	harga double precision
);

CREATE TABLE Aktor(
	id SERIAL PRIMARY KEY,
	nama varchar (50) NOT NULL UNIQUE
);

CREATE TABLE Genre(
	id SERIAL PRIMARY KEY,
	nama varchar (20) NOT NULL UNIQUE
);

CREATE TABLE Users(
	email varchar (50) PRIMARY KEY,
	nama varchar (50),
	pass varchar (20),
	role varchar (10)
);

CREATE TABLE Keranjang(
	emailU varchar (50) REFERENCES Users(email),
	idFilm int REFERENCES Film(id),
	PRIMARY KEY (emailU, idFilm)
);

CREATE TABLE Peminjaman(
	id SERIAL PRIMARY KEY,
	emailU varchar (50) REFERENCES Users(email),
	idFilm int REFERENCES Film(id),
	tanggal DATE
);

CREATE TABLE Pengembalian(
	id SERIAL PRIMARY KEY,
	emailU varchar (50) REFERENCES Users(email),
	idFilm int REFERENCES Film(id),
	tanggal DATE,
	denda double precision
);	

CREATE TABLE GenreFilm(
	idFilm int REFERENCES Film(id),
	idGenre int REFERENCES Genre(id),
	PRIMARY KEY (idGenre, idFilm)
);

CREATE TABLE AktorFilm(
	idFilm int REFERENCES Film(id),
	idAktor int REFERENCES Aktor(id),
	PRIMARY KEY (idAktor, idFilm)
);

