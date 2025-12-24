$(document).ready(function(){

});

async function createMovie() {
    //Creamos el objeto movie
    let movie = {

    }

    // Tomamos los datos que nos han pasado por el formulario del HTML en cada campo
    movie.title = document.querySelector('#inputTitle').value;
    movie.director = document.querySelector('#inputDirector').value;
    movie.description = document.querySelector('#inputSinopsis').value;
    // Parseamos los datos que vienen en formato numérico
    movie.year = parseInt(document.querySelector('#inputYear').value);
    movie.votes = 1; // Damos valor 1 al crear la película, siempre como hay voto pues le damos valor 1
    movie.rating = parseInt(document.querySelector('#inputVote').value);
    movie.imageUrl = document.querySelector('#inputImageURL').value;
    
    // Tomamos los span de los avisos de error al introducir datos
    let warningYear = document.querySelector('#warningYear');
    let warningVote = document.querySelector('#warningVote');
    let warningURL = document.querySelector('#warningURL');
    // Ponemos en true el hidden de todos los span que dan mensaje de error
    warningURL.hidden = true;
    warningVote.hidden = true;
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
    // Validación de nota
    if (movie.rating>10 || movie.rating<0 || !movie.rating || isNaN(movie.rating)){
        warningVote.style.color = "red"
        warningVote.textContent = "La nota debe ser entre 0 y 10"
        warningVote.hidden = false;
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

    // Usamos el fetch para enviar los datos al Backend
    const response = await fetch('/api/movies', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        // Formateamos el cuerpo del mensaje
        body: JSON.stringify(movie) 
    });
    // Damos respuesta correcta o incorrecta
    if (response.ok){
        alert('Película creada correctamente');
        window.location.href = 'movies.html'
    } else {
        alert ('ERROR al crear la película');
    } 
    // TODO: implementar votacion
}