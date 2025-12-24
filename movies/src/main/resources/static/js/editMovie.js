$(document).ready(function () {
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
        document.querySelector('#idMovie').textContent = movie.id
        document.querySelector('#inputTitle').value = movie.title; 
        document.querySelector('#inputDirector').value = movie.director; 
        document.querySelector('#inputSinopsis').value = movie.description; 
        document.querySelector('#inputImageURL').value = movie.imageUrl; 
        document.querySelector('#inputYear').value = movie.year; 
        //document.querySelector('#inputVote').value = movie.votes; // No tiene sentido ya que es un voto calculado
        //document.querySelector('#inputRating').value = movie.rating; // No tiene sentido editar aquí
    } else {
        alert('Error al cargar película');
    }
}

async function PutMovie() {
    const id = document.querySelector('#idMovie').textContent;// esta línea esta dando error

    const movie = {
        id: id,
        title: document.querySelector('#inputTitle').value,
        director: document.querySelector('#inputDirector').value,
        description: document.querySelector('#inputSinopsis').value,
        imageUrl: document.querySelector('#inputImageURL').value,
        year: parseInt(document.querySelector('#inputYear').value)
    };

    // Tomamos los span de los avisos de error al introducir datos
    let warningYear = document.querySelector('#warningYear');
    let warningVote = document.querySelector('#warningVote');
    let warningURL = document.querySelector('#warningURL');
    // Ponemos en true el hidden de todos los span que dan mensaje de error
    warningURL.hidden = true;
    warningYear.hidden = true;
    // Bandera de validaciones
    let validationOk = true; 
    // Guardamos el año actual
    let currentYear = new Date().getFullYear();
    // Validación de año
    if (movie.year>currentYear || !movie.year || isNaN(movie.year)){
        warningYear.style.color = "red"
        warningYear.textContent = "Año debe ser inferior o igual al actual"
        warningYear.hidden = false;
        validationOk = false;
    } 
    
    // Validación de URL de filmaffinity
    if (!movie.imageUrl.startsWith('https://pics.filmaffinity.com/') || !movie.imageUrl.endsWith('.jpg')){
        warningURL.style.color = "red"
        warningURL.textContent = "La URL no es correcta, debe ser de Filmaffinity, empieza por https"
        warningURL.hidden = false;
        validationOk = false;  
    }
    // Si ninguna validación falla sigue corriendo
    if (!validationOk){
        return;
    }

    const response = await fetch('api/movies/' + id, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(movie),
    });

    
    if (response.ok){
        const updated = await response.json();
        alert('Película actualizada')
        window.location.href = 'movies.html'
    } else {
        alert('Error al actualizar');
    }
}