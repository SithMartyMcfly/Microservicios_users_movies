package sithmcfly.movies.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sithmcfly.movies.models.Movie;
import sithmcfly.movies.repositories.MovieRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController // Indica que va a ser una API REST
@RequestMapping("api/movies") // Identifica la ruta para usar los recursos
public class MovieController {
    
    @Autowired //evita tener que generar objetos cada vez que hay peticiones
    private MovieRepository movieRepository;

    @CrossOrigin // Para poder usar los endpoints con aplicaciones externas (Front u otras apps)
    @GetMapping //Atiende todas las peticiones que vayan por GET  
    public List<Movie> GetAllMovies() {
        return movieRepository.findAll();
    }

  
    @CrossOrigin
    @GetMapping("/{id}") //Hay que diferenciar los GET, y esta le damos un path diferente
                        // tenemos que anotarlo en los parametros del método que le diremos que
                        // por la URI vendrá el parametro del método
    public ResponseEntity<Movie> GetMovieById (@PathVariable Long id) {

        Optional<Movie> movie = movieRepository.findById(id);
        // Devolvemos el estado si se encuentra con un ok y en caso de no encontrarlo con una función
        // anónima devolvemos NotFound

        return movie.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Movie> CreateMovie (@RequestBody Movie movie) {

        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie (@PathVariable Long id) {
        if(!movieRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Movie> EditMovie (@PathVariable Long id, @RequestBody Movie updatedMovie){
        
        if(!movieRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        updatedMovie.setId(id);
        Movie savedMovie = movieRepository.save(updatedMovie);
        return ResponseEntity.ok(savedMovie);
    }

    @CrossOrigin
    @GetMapping("/vote/{id}/{rating}")
    public ResponseEntity<Movie> voteMovie(@PathVariable Long id, @PathVariable double rating) {

         if(!movieRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Buscamos la pelicula que se va a votar usamos Optional porque no sabemos si la va a encontrar
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        Movie movie = optionalMovie.get();
        // Usamos getters para traer las votaciones y las notas
        double newRating = ((movie.getVotes() * movie.getRating()) + rating) / (movie.getVotes() + 1);
        // Seteamos los nuevos valores
        movie.setVotes(movie.getVotes()+1);
        movie.setRating(newRating);
        // Guardamos la película y devolvemos en un ok()
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);

    }
}
