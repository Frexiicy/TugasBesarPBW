const header = document.querySelector('.header');
window.addEventListener('scroll', () => {
    if (window.scrollY > 50) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }
});

//BAGIAN FILM BERDASARKAN JUDUL
// Event listener untuk tombol pencarian judul
document.getElementById('titleSearchButton').addEventListener('click', searchFilmsByTitle);

// Event listener untuk tombol Enter di search bar judul
document.getElementById('title').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        searchFilmsByTitle();
    }
});

// Fungsi untuk mencari film berdasarkan judul
function searchFilmsByTitle() {
    const searchQuery = document.getElementById('title').value;

    // Mengambil data film berdasarkan pencarian judul
    fetch(`/tambahFilm/search?title=${encodeURIComponent(searchQuery)}`)
        .then(response => response.json())
        .then(films => {
            const searchResults = document.getElementById('titleResults');

            // Jika tidak ada film yang ditemukan
            if (films.length === 0) {
                searchResults.innerHTML = '<p>No films found.</p>';
            } else {
                searchResults.innerHTML = ''; // Hapus hasil sebelumnya
                displayFilmsInSearch(films, searchResults); // Menampilkan film yang ditemukan
            }
        })
        .catch(error => console.error('Error fetching films:', error));
}

// Fungsi untuk menampilkan film yang ditemukan dalam hasil pencarian
function displayFilmsInSearch(films, container) {
    container.innerHTML = films.map(film => {
        const imageName = film.judul.toLowerCase().replace(/\s+/g, '-'); 
        return `
            <div class="film-card">
                <img src="/assets/film/${imageName}.jpg" alt="${film.judul}" title="${film.judul}">
                <h3>${film.judul}</h3>
                <p class="rating">⭐ ${film.rating}</p>
                <p class="batas-usia">Age: ${film.batas_usia}</p>
            </div>
        `;
    }).join('');
}

//BAGIAN FILM BERDASARKAN AGE
// Event listener untuk tombol pencarian usia
document.getElementById('ageSearchButton').addEventListener('click', searchFilmsByAge);

// Event listener untuk tombol Enter di search bar usia
document.getElementById('age').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        searchFilmsByAge();
    }
});

// Fungsi untuk mencari film berdasarkan batas usia
function searchFilmsByAge() {
    const ageQuery = document.getElementById('age').value;

    // Mengambil data film berdasarkan pencarian usia
    fetch(`/tambahFilm/search-by-age?age=${encodeURIComponent(ageQuery)}`)
        .then(response => response.json())
        .then(films => {
            const searchResults = document.getElementById('ageResults');

            // Jika tidak ada film yang ditemukan
            if (films.length === 0) {
                searchResults.innerHTML = '<p>No films found for the specified age limit.</p>';
            } else {
                searchResults.innerHTML = ''; // Hapus hasil sebelumnya
                displayFilmsInSearch(films, searchResults); // Menampilkan film yang ditemukan
            }
        })
        .catch(error => console.error('Error fetching films:', error));
}

//BAGIAN FILM BERDASARKAN Genre
document.addEventListener('DOMContentLoaded', function () {
    // Ambil data genre dari server saat halaman dimuat
    fetch('/genres/all')
        .then(response => response.json())
        .then(genres => {
            const genreSelect = document.getElementById('genre');
            genreSelect.innerHTML = `<option value="">Select Genre</option>` + genres.map(genre => `
                <option value="${genre.id}">${genre.nama}</option>
            `).join('');
        })
        .catch(error => console.error('Error fetching genres:', error));

    document.getElementById('genreSearchButton').addEventListener('click', searchFilmsByGenre);
    document.getElementById('genre').addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            searchFilmsByGenre();
        }
    });

    function searchFilmsByGenre() {
        const genreId = document.getElementById('genre').value;

        if (!genreId) {
            alert('Please select a genre!');
            return;
        }

        // Mengambil data film berdasarkan genre yang dipilih
        fetch(`/tambahFilm/search-by-genre?genreId=${encodeURIComponent(genreId)}`)
            .then(response => response.json())
            .then(films => {
                const searchResults = document.getElementById('genreResults');
                if (films.length === 0) {
                    searchResults.innerHTML = '<p>No films found for the selected genre.</p>';
                } else {
                    searchResults.innerHTML = '';
                    displayFilmsInSearch(films, searchResults);
                }
            })
            .catch(error => console.error('Error fetching films by genre:', error));
    }

    function displayFilmsInSearch(films, container) {
        container.innerHTML = films.map(film => {
            const imageName = film.judul.toLowerCase().replace(/\s+/g, '-');
            return `
                <div class="film-card">
                    <img src="/assets/film/${imageName}.jpg" alt="${film.judul}" title="${film.judul}">
                    <h3>${film.judul}</h3>
                    <p class="rating">⭐ ${film.rating}</p>
                    <p class="batas-usia">Age: ${film.batas_usia}</p>
                </div>
            `;
        }).join('');
    }
});

//BAGIAN FILM BERDASARKAN Aktor
// Event listener untuk tombol pencarian aktor
document.getElementById('actorSearchButton').addEventListener('click', searchFilmsByActor);

// Event listener untuk tombol Enter di search bar aktor
document.getElementById('actor').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        searchFilmsByActor();
    }
});

// Fungsi untuk mencari film berdasarkan aktor
function searchFilmsByActor() {
    const actorName = document.getElementById('actor').value;

    if (!actorName) {
        document.getElementById('actorResults').innerHTML = '<p>Please enter an actor name.</p>';
        return;
    }

    // Mengambil data aktor berdasarkan pencarian nama aktor
    fetch(`/aktor/search-by-name?name=${encodeURIComponent(actorName)}`)
        .then(response => response.json())
        .then(actors => {
            console.log("Actors found:", actors); // Debug log
            if (actors.length === 0) {
                document.getElementById('actorResults').innerHTML = '<p>No actors found.</p>';
                return;
            }

            // Ambil ID aktor yang pertama dari hasil pencarian
            const actorId = actors[0].id;
            console.log("Actor ID:", actorId); // Debug log

            // Mengambil data film berdasarkan ID aktor
            fetch(`/tambahFilm/search-by-actor?actorId=${encodeURIComponent(actorId)}`)
                .then(response => response.json())
                .then(films => {
                    console.log("Films found:", films); // Debug log
                    const actorResults = document.getElementById('actorResults');

                    // Jika tidak ada film yang ditemukan
                    if (films.length === 0) {
                        actorResults.innerHTML = '<p>No films found for this actor.</p>';
                    } else {
                        actorResults.innerHTML = ''; // Hapus hasil sebelumnya
                        displayFilmsInSearch(films, actorResults); // Menampilkan film yang ditemukan
                    }
                })
                .catch(error => console.error('Error fetching films by actor:', error));
        })
        .catch(error => console.error('Error fetching actor:', error));
}

// Fungsi untuk menampilkan film yang ditemukan dalam hasil pencarian
function displayFilmsInSearch(films, container) {
    container.innerHTML = films.map(film => {
        const imageName = film.judul.toLowerCase().replace(/\s+/g, '-');
        return `
            <div class="film-card">
                <img src="/assets/film/${imageName}.jpg" alt="${film.judul}" title="${film.judul}">
                <h3>${film.judul}</h3>
                <p class="rating">⭐ ${film.rating}</p>
                <p class="batas-usia">Age: ${film.batas_usia}</p>
            </div>
        `;
    }).join('');
}