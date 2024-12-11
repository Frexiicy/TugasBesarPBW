INSERT INTO Film (judul, rating, sinopsis, batas_usia, stok, harga)
VALUES
	('Wicked', 8, 'Elphaba, a misunderstood young woman because of her green skin, and Glinda, a popular girl, become friends at Shiz University in the Land of Oz. After an encounter with the Wonderful Wizard of Oz, their friendship reaches a crossroads.', '13+ (PG)', 5, 30000),
	('Moana 2', 7, 'After receiving an unexpected call from her wayfinding ancestors, Moana must journey to the far seas of Oceania and into dangerous, long-lost waters for an adventure unlike anything shes ever faced.', '13+ (PG)', 5, 30000),
	('Gladiator II',7, 'After his home is conquered by the tyrannical emperors who now lead Rome, Lucius is forced to enter the Colosseum and must look to his past to find strength to return the glory of Rome to its people.', '17+ (R)', 5, 30000),
	('The Substance', 7, 'A fading celebrity takes a black-market drug: a cell-replicating substance that temporarily creates a younger, better version of herself.', '17+ (R)', 5, 30000),
	('Dear Santa', 5, 'When a young boy mails his Christmas wish list to Santa with one crucial spelling error, a devilish Jack Black arrives to wreak havoc on the holidays.', '13+ (PG-13)', 5, 300000),
	('Gladiator', 8, 'A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.', '17+ (R)', 5, 30000),
	('Deadpool & Wolverine', 7, 'Deadpool is offered a place in the Marvel Cinematic Universe by the Time Variance Authority, but instead recruits a variant of Wolverine to save his universe from extinction.', '17+', 5, 30000),
	('The Lord of the Rings: The War of the Rohirrim', 7, 'A sudden attack by Wulf, a clever and ruthless Dunlending lord seeking vengeance for the death of his father, forces Helm Hammerhand, the King of Rohan, and his people to make a daring last stand in the ancient stronghold of the Hornburg.', '13+ (PG-13)', 5, 30000),
	('The Equalizer 3', 7, 'Robert McCall finds himself at home in Southern Italy but he discovers his friends are under the control of local crime bosses. As events turn deadly, McCall knows what he has to do: become his friends protector by taking on the mafia.', '17+', 5, 30000),
	('Our Little Secret', 6, 'Two resentful exes must awkwardly spend Christmas together after they learn that their new partners are siblings.', 'TV-14', 5, 30000),
	('Red One', 7, 'After Santa Claus is kidnapped, the North Poles Head of Security must team up with a notorious hacker in a globe-trotting, action-packed mission to save Christmas.', 'PG-13', 5, 30000),
	('WrestleMania XL', 9, 'The Undisputed WWE Universal Championship will be on the line when Roman Reigns defends his title against the winner of the 2024 Royal Rumble, Cody Rhodes. The Scottish Warrior Drew McIntyre will finally get his chance to win a world title in front of the masses as he faces World Heavyweight Champion Seth "Freakin" Rollins at The Showcase of the Immortals. Plus a big tag team match on night 1 where Cody Rhodes and World Heavyweight Champion Seth "Freakin" Rollins will go head to head with Dwayne "The Rock" Johnson and Undisputed WWE Universal Champion Roman Reigns in a match with huge WrestleMania implications. Bayley is hellbent on seizing the WWE Womens Champion when she goes to war against her former ally IYO SKY at WrestleMania. Mami Rhea Ripley defends her Womens World Title against The Man Becky Lynch in what should be a legendary championship fight and many more.', 'TV-PG', 5, 30000),
	('Fast X', 6, 'Dom Toretto and his family are targeted by the vengeful son of drug kingpin Hernan Reyes.', 'PG-13', 5, 30000);

INSERT INTO Aktor (nama)
VALUES
	('Cynthia Erivo'), ('Ariana Grande'), ('Jeff Goldblum'),
	('Aulii Cravalho'), ('Dwayne Johnson'), ('Hualalai Chung'),
	('Paul Mescal'), ('Denzel Washington'), ('Pedro Pascal'),
	('Demi Moore'), ('Margaret Qualley'), ('Dennis Quaid'),
	('Jack Black'), ('Robert Timothy Smith'), ('Keegan-Michael Key'),
	('Russel Crowe'), ('Joaquin Phoenix'), ('Connie Nielsen'),
	('Ryan Reynolds'), ('Hugh Jackman'), ('Emma Corrin'),
	('Brian Cox'), ('Gaia Wise'), ('Miranda Otto'),
	('Dakota Fanning'), ('Eugenio Mastrandrea'),
	('Lindsay Lohan'), ('Ian Harding'), ('Kristin Chenoweth'),
	('Chris Evans'), ('Lucy Liu'),
	('Joe Anoai'), ('Cody Rhodes'),
	('Vin Diesel'), ('Michelle Rodriguez'), ('Jason Statham');

INSERT INTO Genre (nama)
VALUES
	('Fantasy'), ('Musical'), ('Romance'),
	('Animation'), ('Adventure'), ('Comedy'),
	('Action'), ('Drama'), ('Horror'),
	('Crime'), ('Thriller'), ('Sport'),
	('Mystery');

INSERT INTO GenreFilm (idFilm, idGenre)
VALUES
	(1,1), (1,2), (1,3),
	(2,4), (2,5), (2,6),
	(3,7), (3,8), (3,5),
	(4,8), (4,9),
	(5,6), (5,1), (5,9),
	(6,7), (6,5), (6,8),
	(7,7), (7,5), (7,6),
	(8,4), (8,7), (8,5),
	(9,7), (9,10), (9,11),
	(10,6), (10,3),
	(11,7), (11,5), (11,6),
	(12,7), (12,12),
	(13,7), (13,5), (13,10), (13,13), (13,11);
	

INSERT INTO AktorFilm (idFilm, idAktor)
VALUES
	(1,1), (1,2), (1,3),
	(2,4), (2,5), (2,6),
	(3,7), (3,8), (3,9),
	(4,10), (4,11), (4,12),
	(5,13), (5,14), (5,15),
	(6,16), (6,17), (6,18),
	(7,19), (7,20), (7,21),
	(8,22), (8,23), (8,24),
	(9,8), (9,25), (9,26),
	(10,27), (10,28), (10,29),
	(11,5), (11,30), (11,31),
	(12,32), (12,33), (12,5),
	(13,34), (13,35), (13,36);

INSERT INTO Users (nama, email, pass, role)
VALUES
	('Kapi', 'kapi@gmail.com', '12345678', 'admin'),
	('Dodo', 'dodo@gmail.com', '12345678', 'user'),
	('Alice', 'alice@gmail.com', '12345678', 'user'),
	('John', 'john@gmail.com', '12345678', 'user');

INSERT INTO Keranjang (idUser, idFilm)
VALUES
	(2,3), (2,4), (2,8), (2,11), (2,12), (2,13),
	(3,1), (3,2), (3,5), (3,7), (3,10), (3,11),
	(4,2), (4,4), (4,6), (4,7), (4,10), (4,12);

INSERT INTO Peminjaman (idUser, idFilm, tanggal)
VALUES
	(2,2,'2024-12-01'), (2,5,'2024-12-01'),
	(3,3,'2024-12-02'), (3,4,'2024-12-02'), (3,8,'2024-12-05'),
	(4,1,'2024-12-07'), (4,5,'2024-12-07');

INSERT INTO Pengembalian (idUser, idFilm, tanggal, denda)
VALUES
	(2,1,'2024-12-01',0), (3,13,'2024-12-02',10000),(3,12,'2024-12-02',10000),
	(4,3,'2024-12-07',0);

--SELECT
SELECT * FROM Film;
SELECT * FROM Aktor;
SELECT * FROM Genre;
SELECT * FROM GenreFilm;
SELECT * FROM AktorFilm;
SELECT * FROM Users;
SELECT * FROM Keranjang;
SELECT * FROM Peminjaman;
SELECT * FROM Pengembalian;