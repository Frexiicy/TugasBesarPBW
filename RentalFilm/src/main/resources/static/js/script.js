//Fungsi untuk slideshow
let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const totalSlides = slides.length;

function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.style.opacity = i === index ? '1' : '0';
    });
}

function changeSlide(direction) {
    currentSlide = (currentSlide + direction + totalSlides) % totalSlides;
    showSlide(currentSlide);
}

// Aktor - Menampilkan detail film saat klik aktor
const actorPosters = document.querySelectorAll('.actor_Poster');
const actorDetails = document.getElementById('actor-details');

actorPosters.forEach((poster) => {
    poster.addEventListener('click', () => {
        const actor = poster.getAttribute('data-actor');
        displayActorDetails(actor);
    });
});

function displayActorDetails(actor) {
    // Simulasi data untuk film aktor
    const actorMovies = {
        cillian: [
            { title: "Peaky Blinders", image: "/assets/film/peaky-blinders.jpg" },
            { title: "Oppenheimer", image: "/assets/film/oppenheimer.jpeg" },
        ],
        'tom-cruise': [
            { title: "Mission Impossible", image: "/assets/film/mission-impossible.jpg" },
            { title: "Top Gun", image: "/assets/film/top-gun.jpg" },
        ],
        'paul-walker': [
            { title: "Fast & Furious", image: "/assets/film/fastFurious.jpg" },
            { title: "Into the Blue", image: "/assets/film/into-the-blue.jpg" },
        ],
    };

    const movies = actorMovies[actor] || [];
    actorDetails.innerHTML = `
        <h3>${actor.charAt(0).toUpperCase() + actor.slice(1).replace('-', ' ')}</h3>
        <div class="actor-movies">
            ${movies.map((movie) => `
                <div class="movie">
                    <img src="${movie.image}" alt="${movie.title}">
                    <p>${movie.title}</p>
                </div>
            `).join('')}
        </div>
    `;
    actorDetails.style.display = 'block';
}
