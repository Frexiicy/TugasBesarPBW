let currentPage = 0;
const size = 8; // Jumlah aktor per halaman

// Fungsi untuk memuat aktor berdasarkan halaman
function loadPage(page) {
    if (page < 0) return; // Jangan izinkan halaman negatif

    currentPage = page;
    document.getElementById('pageNumber').textContent = `Page ${currentPage + 1}`;

    fetch(`/aktor/paginated-list?page=${currentPage}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            const actorGrid = document.getElementById('actorGrid');
            actorGrid.innerHTML = ''; // Hapus konten sebelumnya

            data.forEach(actor => {
                const actorDiv = document.createElement('div');
                actorDiv.classList.add('actor_Poster');
                actorDiv.dataset.actorName = actor.nama;

                actorDiv.innerHTML = `
                    <img src="/assets/actor/${actor.nama.replace(/\s+/g, '-').toLowerCase()}.jpg" alt="${actor.nama}" title="${actor.nama}">
                    <h3>${actor.nama}</h3>
                `;

                actorGrid.appendChild(actorDiv);
            });

            // Navigasi halaman
            document.getElementById('prevPage').disabled = currentPage === 0;
            document.getElementById('nextPage').disabled = data.length < size;
        })
        .catch(error => console.error('Error fetching actors:', error));
}

// Fungsi untuk memuat film berdasarkan nama aktor
function fetchFilmsByActor(actorName) {
    fetch(`/film/search-by-actor-name?actorName=${encodeURIComponent(actorName)}`)
    .then(response => response.json())  
    .then(films => {
        console.log('Films fetched:', films);  // Log hasil films
        const actorDetails = document.getElementById('actor-details');
        
        // Jika tidak ada film yang ditemukan
        if (films.length === 0) {
            actorDetails.innerHTML = `<p>No films found for ${actorName}.</p>`;
        } else {
            actorDetails.innerHTML = '';  // Hapus hasil sebelumnya
            displayFilmsInActorSearch(films);  // Menampilkan film berdasarkan aktor
        }
    })
    .catch(error => {
        console.error('Error fetching films:', error);
    });

}

// Fungsi untuk menampilkan film yang ditemukan berdasarkan aktor
function displayFilmsInActorSearch(films) {
    const actorDetails = document.getElementById('actor-details');
    actorDetails.innerHTML = films.map(film => {
        // Gunakan nama film yang telah diubah menjadi format URL-friendly
        const filmImageName = film.judul.toLowerCase().replace(/\s+/g, '-'); // Mengganti spasi dengan -

        // Tentukan gambar default jika poster tidak ada
        const filmImageSrc = `/assets/film/${filmImageName}.jpg`;  // Gambar berdasarkan judul film

        return `
            <div class="film-card">
                <img src="${filmImageSrc}" alt="${film.judul}" title="${film.judul}">
                <h3>${film.judul}</h3>
                <p class="rating">⭐ ${film.rating}</p>
                <p class="batas-usia">Age: ${film.batas_usia}</p>
                <p class="sinopsis">${film.sinopsis}</p>
            </div>
        `;
    }).join('');
}

// Event listener untuk klik pada nama aktor
document.addEventListener('click', function (e) {
    const actorPoster = e.target.closest('.actor_Poster');
    if (actorPoster) {
        const actorName = actorPoster.dataset.actorName;
        fetchFilmsByActor(actorName);
    }
});

// Muat halaman pertama saat halaman dimuat
window.onload = function () {
    loadPage(0);
};

// Fungsi untuk slideshow
// Menangani transparansi header saat scroll
const header = document.querySelector('.header');
window.addEventListener('scroll', () => {
    if (window.scrollY > 50) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }
});

// Menangani slideshow otomatis
let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const totalSlides = slides.length;

// Fungsi untuk menampilkan slide
function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.style.opacity = i === index ? '1' : '0';
    });
}

// Fungsi untuk mengubah slide
function changeSlide(direction) {
    currentSlide = (currentSlide + direction + totalSlides) % totalSlides;
    showSlide(currentSlide);
}

// Menjalankan slideshow otomatis setiap 5 detik
let autoSlideInterval = setInterval(() => {
    changeSlide(1);
}, 5000);

// Tampilkan slide pertama saat halaman dimuat
showSlide(currentSlide);

// Fungsi untuk mengambil film berdasarkan rating
async function fetchFilmsByRating(rating = null) {
    try {
        const url = rating ? `/film/by-rating?rating=${rating}` : '/film/all';
        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to fetch films');
        
        const films = await response.json();
        
        if (films.length === 0) {
            // Jika tidak ada film yang ditemukan
            document.getElementById('filmGrid').innerHTML = '<p>No films found.</p>';
        } else {
            displayFilms(films);
        }
    } catch (error) {
        console.error('Error fetching films:', error);
    }
}

function displayFilms(films) {
    const filmGrid = document.getElementById('filmGrid');
    
    // Menangani jika tidak ada film yang ditemukan
    if (films.length === 0) {
        filmGrid.innerHTML = '<p>No films found.</p>';
        // Jika tidak ada film, hapus tampilan pagination
        document.getElementById('pagination').innerHTML = '';
        return; // Keluarkan dari fungsi agar tidak melanjutkan ke rendering pagination
    }

    const filmsPerPage = 5;
    const totalPages = Math.ceil(films.length / filmsPerPage);

    let currentPage = 1;

    function renderPage(page) {
        const start = (page - 1) * filmsPerPage;
        const end = start + filmsPerPage;
        const paginatedFilms = films.slice(start, end);

        filmGrid.innerHTML = paginatedFilms.map(film => {
            // Format nama file agar sesuai dengan standar (huruf kecil dan tanpa spasi)
            const imageName = film.judul.toLowerCase().replace(/\s+/g, '-'); 
            return `
                <div class="film-card" data-id="${film.id}">
                    <img src="/assets/film/${imageName}.jpg" alt="${film.judul}" title="${film.judul}">
                    <h3>${film.judul}</h3>
                    <p class="rating">⭐ ${film.rating}</p>
                    <p class="batas-usia">Age: ${film.batas_usia}</p>
                </div>
            `;
        }).join('');

        addCardClickListeners();
        renderPagination(page, totalPages);
    }

    function renderPagination(current, total) {
        const pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

        for (let i = 1; i <= total; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            pageButton.className = i === current ? 'active' : '';
            pageButton.addEventListener('click', () => {
                renderPage(i);
            });
            pagination.appendChild(pageButton);
        }
    }
    
    function addCardClickListeners(){
        const filmCards = document.querySelectorAll('.film-card');
        filmCards.forEach(card => {
            card.addEventListener('click', () =>{
                const idFilm = card.getAttribute('data-id');
                window.location.href = `/film/detail?id=${idFilm}`;
            })
        })
    }

    renderPage(currentPage);
}

// Event Listener untuk tombol filter
document.getElementById('filterButton').addEventListener('click', () => {
    const rating = document.getElementById('rating').value;
    // Jika rating kosong, panggil API untuk menampilkan semua film
    fetchFilmsByRating(rating || null);
});

// Load film awal berdasarkan semua data
document.addEventListener('DOMContentLoaded', () => {
    // Menampilkan semua film saat pertama kali halaman dimuat
    fetchFilmsByRating(null);
});


// Event listener untuk tombol pencarian
document.querySelector('.search_Button').addEventListener('click', searchFilms);

// Event listener untuk tombol Enter di search bar
document.querySelector('.searchbar').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        searchFilms();
    }
});

// Fungsi untuk mencari film
function searchFilms() {
    const searchQuery = document.querySelector('.searchbar').value;

    // Mengambil data film berdasarkan pencarian judul
    fetch(`/film/search?title=${searchQuery}`)
        .then(response => response.json())
        .then(films => {
            const searchResults = document.getElementById('searchResults');
            
            // Jika tidak ada film yang ditemukan
            if (films.length === 0) {
                searchResults.innerHTML = '<p>No films found.</p>';
            } else {
                searchResults.innerHTML = '';  // Hapus hasil sebelumnya
                displayFilmsInSearch(films);  // Menampilkan film yang ditemukan
            }
        })
        .catch(error => console.error('Error fetching films:', error));
}

// Fungsi untuk menampilkan film yang ditemukan dalam hasil pencarian
function displayFilmsInSearch(films) {
    const searchResults = document.getElementById('searchResults');
    searchResults.innerHTML = films.map(film => {
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