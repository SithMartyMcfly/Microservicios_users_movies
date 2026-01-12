package sithmcfly.movies.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.http.request.VoteRequest;
import sithmcfly.movies.http.response.VoteResponse;
import sithmcfly.movies.service.ImpMovieService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin // Para poder usar los endpoints con aplicaciones externas (Front u otras apps)
@RestController // Indica que va a ser una API REST
@RequestMapping("api/movies") // Identifica la ruta para usar los recursos
public class MovieController {

    private final ImpMovieService impMovieService;
    
    public MovieController (ImpMovieService impMovieService){
        this.impMovieService = impMovieService;
    }

    @GetMapping //Atiende todas las peticiones que vayan por GET  
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(impMovieService.findAll());
    }

  

    @GetMapping("/{id}") /*Hay que diferenciar los GET, y esta le damos un path diferente
                        tenemos que anotarlo en los parametros del método que le diremos que
                        por la URI vendrá el parametro del método*/
    public ResponseEntity<Movie> getMovieById (@PathVariable Long id) {

        return ResponseEntity.ok(impMovieService.findById(id));
    }


    @PostMapping
    public ResponseEntity<Movie> createMovie (@RequestBody Movie movie) {
        Movie createdMovie = impMovieService.createMovie(movie);
        return ResponseEntity
                // Devolvemos el estado
                .status(HttpStatus.CREATED)
                // Devolvemos lo que hemos creado, podriamos generar un DTO para respuestas
                .body(createdMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie (@PathVariable Long id) {
        impMovieService.deleteMovie(id);
        // Un método de borrado no debe devolver nada de ahi que usemos el status NO CONTENT
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Movie> editMovie (@PathVariable Long id, @RequestBody Movie updatedMovie){
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(impMovieService.editMovie(id, updatedMovie));
    }


    @PutMapping("/vote/{id}")
    public ResponseEntity<VoteResponse> voteMovie(@PathVariable Long id, @RequestBody VoteRequest voteRequest) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(impMovieService.voteMovie(id, voteRequest));
    }
}
