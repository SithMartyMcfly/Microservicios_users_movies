package sithmcfly.movies.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import sithmcfly.movies.DTO.MovieDTO;
//import sithmcfly.movies.DTO.UserDTO;
//import sithmcfly.movies.client.UserClient;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.exception.MovieNotFoundException;
import sithmcfly.movies.http.request.MovieRequestDTO;
import sithmcfly.movies.http.request.VoteRequest;
//import sithmcfly.movies.http.response.UsersByMovieResponse;
import sithmcfly.movies.http.response.MovieResponseCreateDTO;
import sithmcfly.movies.http.response.MovieResponseUpdateDTO;
import sithmcfly.movies.http.response.VoteResponse;
import sithmcfly.movies.mappers.MovieMapper;
import sithmcfly.movies.persistence.MovieRepository;

@Service
public class ImpMovieService implements IMovieService{
    private final MovieRepository movieRepository;
    //private UserClient userClient;

    //Inyectamos por constructor para facilitar el testeo y buenas prácticas
    public ImpMovieService (MovieRepository movieRepository){
        this.movieRepository = movieRepository;
        //this.userClient = userClient;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
       List<Movie> movieList = movieRepository.findAll();
       return movieList.stream()
                .map(MovieMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO getMovie(long id) {
        Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new MovieNotFoundException(id));

        return MovieMapper.toResponseDTO(movie);
    }


    @Override
    public MovieResponseCreateDTO createMovie(MovieRequestDTO movieCreate) {
        Movie movie = MovieMapper.toEntity(movieCreate);
        movieRepository.save(movie);
        return MovieMapper.toMovieCreateResponseDTO(movie);
    }

    @Override
    public MovieResponseUpdateDTO editMovie(MovieRequestDTO request, long id) {
        Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new MovieNotFoundException(id));
        
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDirector(request.getDirector());
        movie.setYear(request.getYear());
        movie.setImageUrl(request.getImageUrl());

        movieRepository.save(movie);

        return MovieMapper.toUpdateResponseDTO(movie);
    }

    @Override
    public void deleteMovie(long id) {
        //Comprobamos que exista la pelicula con id
        Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new MovieNotFoundException(id));
        //Borramos la película que hemos encontrado, borramos por entidad no por id
        movieRepository.delete(movie);   
    }



    // FUERA DEL CRUD
    @Override
    public VoteResponse voteMovie(VoteRequest voteRequest, long id) {
        // Encontrar la película
        Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new MovieNotFoundException(id));
        // Actualizar puntuación
        int movieVotesPrevious = movie.getVotes();
        int movieVotes = movie.getVotes()+1;

        double newRating = (movieVotesPrevious * movie.getRating() + voteRequest.getRating())/movieVotes;
        movie.setVotes(movieVotes);
        movie.setRating(newRating);
        movieRepository.save(movie);

        return new VoteResponse(movieVotes, newRating);
    }

    //Método consulta microservicio Users
    /*@Override
    public UsersByMovieResponse findUsersByMovie(Long idMovie) {
        // Consultamos la Movie
        Movie movie = movieRepository
                    .findById(idMovie)
                    .orElseThrow(() -> new MovieNotFoundException(idMovie));
        // Obtenemos los usuarios, inyectando el cliente UserClient
        List<UserDTO> userDTOList = userClient.findUserByMovie(idMovie);
        
        UsersByMovieResponse response = new UsersByMovieResponse();

        response.setMovieName(movie.getTitle());
        response.setDirector(movie.getDirector());
        response.setUserList(userDTOList);

        return response;
    }*/

    

}