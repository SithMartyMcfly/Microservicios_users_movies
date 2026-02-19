package sithmcfly.movies.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.http.request.MovieRequestDTO;
import sithmcfly.movies.http.request.VoteRequest;
import sithmcfly.movies.http.response.MovieResponseCreateDTO;
import sithmcfly.movies.http.response.MovieResponseUpdateDTO;
import sithmcfly.movies.http.response.VoteResponse;
import sithmcfly.movies.service.ImpMovieService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;




@Tag(name = "Movies", description = "Operaciones CRUD de Películas y puntuación de películas")
@CrossOrigin // Para poder usar los endpoints con aplicaciones externas (Front u otras apps)
@RestController // Indica que va a ser una API REST
@RequestMapping("api/movies") // Identifica la ruta para usar los recursos
public class MovieController {

    private final ImpMovieService impMovieService;
    
    public MovieController (ImpMovieService impMovieService){
        this.impMovieService = impMovieService;
    }

    @Operation(summary = "Listar Películas", description = "Encuentra un listado de todas las películas en el sistema")
    @ApiResponse(responseCode = "200", description = "Listado de usuarios encontrado")
    @GetMapping //Atiende todas las peticiones que vayan por GET  
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(impMovieService.getAllMovies());
    }

  
    @Operation(summary = "Encuentra una película", description = "Encuentra una película pasando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description ="Película encontrada correctamente"),
        @ApiResponse(responseCode = "404", description = "La película no fue encontrada")
    })
    @GetMapping("/{id}") /*Hay que diferenciar los GET, y esta le damos un path diferente
                        tenemos que anotarlo en los parámetros del método que le diremos que
                        por la URI vendrá el parametro del método*/
    public ResponseEntity<MovieDTO> getMovie (@PathVariable Long id) {

        return ResponseEntity.ok(impMovieService.getMovie(id));
    }

    @Operation(summary = "Crea una película", description = "Crea un nuevo usuario con validaciones propias de MovieRequestDTO")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Película creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos incorrectos o incompletos")
    })
    @PostMapping
    public ResponseEntity<MovieResponseCreateDTO> createMovie (@Valid @RequestBody MovieRequestDTO movie) {
        MovieResponseCreateDTO createdMovie = impMovieService.createMovie(movie);
        return ResponseEntity
                // Devolvemos el estado
                .status(HttpStatus.CREATED)
                // Devolvemos lo que hemos creado, podriamos generar un DTO para respuestas
                .body(createdMovie);
    }

    @Operation(summary = "Borrar una película", description = "Borra una película a partir de una Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película borrada correctamente"),
        @ApiResponse(responseCode = "404", description = "Id película no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie (@PathVariable Long id) {
        impMovieService.deleteMovie(id);
        // Un método de borrado no debe devolver nada de ahi que usemos el status NO CONTENT
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Actualizar película", description = "Permite actualizar una película pasandole un MovieRequestDTO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Película no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseUpdateDTO> editMovie (@PathVariable Long id, @Valid @RequestBody MovieRequestDTO updatedMovie){
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(impMovieService.editMovie(updatedMovie, id));
    }


    @Operation( summary = "Vota película", 
                description = "Vota una pelicula y hace un cálculo de su media con la nueva nota, devuelve el número de votos y la media de notas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película votada correctamente"),
        @ApiResponse(responseCode = "404", description = "La película no fue encontrada")
    })
    @PutMapping("/vote/{id}")
    public ResponseEntity<VoteResponse> voteMovie(@PathVariable Long id, @Valid @RequestBody VoteRequest voteRequest) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(impMovieService.voteMovie(voteRequest, id));
    }

    /*@GetMapping("/movie/{idMovie}/users")
    public ResponseEntity<?> findUsersById(@RequestParam Long idMovie) {
        return ResponseEntity.ok(impMovieService.findUsersByMovie(idMovie));
    }*/
    
}
