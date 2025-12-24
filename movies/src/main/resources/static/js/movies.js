// Call the dataTables jQuery plugin
$(document).ready(function () {
  loadMovies();
  //$("#dataTable").DataTable(); //PAGINACIÓN
  activeUserMail(); // FUNCIÓN NECESITA DE USERS
});

// Active user FUNCIÓN NECESITA DE USERS
function activeUserMail() {
  // Recuperamos del localStorage el mail y le damos el valor al elemento
  document.querySelector("#txt-id-user").outerHTML = localStorage.email;
}

//  FUNCIÓN QUE CARGA LAS PELICULAS

async function loadMovies() {
  console.log("Ejecuto carga...");
  const request = await fetch("/api/movies", {
    method: "GET",
  });

  const movies = await request.json();
  console.log(movies);
  let tbody = document.querySelector("#users tbody");
  tbody.innerHTML = "";

  movies.forEach((movie) => {
    let deleteButton = `<a href=# onclick="deleteMovie(${movie.id})" class="btn btn-danger btn-circle btn-sm">
                          <i class="fas fa-eraser"></i>
                        </a>`;
    let viewButton = `<a href="detailMovie.html?id=${movie.id}" class="btn btn-info btn-circle btn-sm">
                          <i class="fas fa-info"></i>
                        </a>`;
    let editButton = `<a href="editMovie.html?id=${movie.id}" class="btn btn-warning btn-circle btn-sm">
                          <i class="fas fa-edit"></i>
                        </a>`;
    
    let movieHTML = `
        <tr>
            <td>${movie.title}</td>
            <td>${movie.director}</td>
            <td>${movie.year}</td>
            <td>${movie.rating}</td>
            <td>${movie.votes}</td>
            <td>
                ${deleteButton} ${viewButton} ${editButton}
            </td>
        </tr>
        `;
    tbody.innerHTML += movieHTML;
  }); 
}


//ELIMINA PELÍCULA
async function deleteMovie(id) {
  if (!confirm("¿Desea eliminar la película?")) {
    // Confirm es una función que saca una ventana de confirmación
    // en el momento que se da a cancelar rompe esta ejecución
    return;
  }
  const request = await fetch("api/movies/" + id, {
    method: "DELETE",
  });
  location.reload();
}
