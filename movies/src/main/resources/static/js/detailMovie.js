$(document).ready(function() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    if(id) {
        getMovie(id);
    } else {
        alert('No existe la película')
    }
});

async function getMovie (id){
    const request = await fetch('api/movies/' + id, {
        method: 'GET'

    });
    
    const movie = await request.json();

    if (request.ok){
        console.log(movie);
        document.querySelector('#inputTitle').innerHTML = movie.title; 
        document.querySelector('#inputDirector').innerHTML = movie.director; 
        document.querySelector('#inputSinopsis').innerHTML = movie.description; 
        document.querySelector('#inputImageURL').src = movie.imageUrl; 
        document.querySelector('#inputImageURL').alt = movie.title; 
        document.querySelector('#inputYear').innerHTML = movie.year; 
        document.querySelector('#inputRating').innerHTML = movie.rating; 
        document.querySelector('#inputVote').innerHTML = movie.votes; 
    } else {
        alert('Error al cargar película');
    }
}
