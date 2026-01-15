package sithmcfly.movies.service;
import java.util.List;

import org.springframework.stereotype.Service;

import sithmcfly.movies.DTO.UserDTO;
import sithmcfly.movies.client.UserClient;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.exception.MovieNotFoundException;
import sithmcfly.movies.http.request.VoteRequest;
import sithmcfly.movies.http.response.UserResponse;
import sithmcfly.movies.http.response.UsersByMovieResponse;
import sithmcfly.movies.http.response.VoteResponse;
import sithmcfly.movies.persistence.MovieRepository;

@Service
public class ImpMovieService implements IMovieService{
    private MovieRepository movieRepository;
    private UserClient userClient;

    //Inyectamos por constructor para facilitar el testeo y buenas prácticas
    public ImpMovieService (MovieRepository movieRepository, UserClient userClient ){
        this.movieRepository = movieRepository;
        this.userClient = userClient;
    }

    @Override
    public List<Movie> findAll() {
       List<Movie> movieList = movieRepository.findAll();
       return movieList;
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        //Comprobamos que exista la pelicula con id
        Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new MovieNotFoundException(id));
        //Borramos la película que hemos encontrado, borramos por entidad no por id
        movieRepository.delete(movie);   
    }

    @Override
    public Movie editMovie(Long id, Movie updatedMovie) {
        Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new MovieNotFoundException(id));

        movie.setTitle(updatedMovie.getTitle());
        movie.setDescription(updatedMovie.getDescription());
        movie.setDirector(updatedMovie.getDirector());
        movie.setYear(updatedMovie.getYear());
        movie.setImageUrl(updatedMovie.getImageUrl());
        // Debemos estudiar si cambiar la puntuación de la película

        return movie; //JPA va a hacer la actualización auto
    }

    @Override
    public VoteResponse voteMovie(Long id, VoteRequest voteRequest) {
        // Encontrar la película
        Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new MovieNotFoundException(id));
        // Actualizar puntuación
        int movieVotesPreviuous = movie.getVotes();
        int movieVotes = movie.getVotes()+1;

        double newRating = (movieVotesPreviuous * movie.getRating() + voteRequest.getRating())/movieVotes;
        movie.setVotes(movieVotes);
        movie.setRating(newRating);
        movieRepository.save(movie);

        return new VoteResponse(movieVotes, newRating);
    }

    //Método consulta microservicio Users
    @Override
    public UsersByMovieResponse findUsersByMovie(Long idMovie) {
        // Consultamos la Movie
        Movie movie = movieRepository
                    .findById(idMovie)
                    .orElseThrow(() -> new MovieNotFoundException(idMovie));
        // Obtenemos los usuarios, inyectando el cliente UserClient
        List<UserDTO> userDTOList = userClient.findUserByMovie(idMovie);
        
        return UsersByMovieResponse.builder()
            .movieName(movie.getTitle())
            .directorName(movie.getDirector())
            .userList(userDTOList)
            .build();
    }

    

}