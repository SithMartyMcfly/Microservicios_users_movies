package sithmcfly.movies.service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestHeader;
import sithmcfly.movies.DTO.MovieDTO;
//import sithmcfly.movies.DTO.UserDTO;
//import sithmcfly.movies.client.UserClient;
import sithmcfly.movies.DTO.UserDTO;
import sithmcfly.movies.client.UserClient;
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
    private final UserClient userClient;

    //Inyectamos por constructor para facilitar el testeo y buenas prácticas
    public ImpMovieService (MovieRepository movieRepository, UserClient userClient){
        this.movieRepository = movieRepository;
        this.userClient = userClient;
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
        //Comprobamos que existe la película con id
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
        String movieTitle = movie.getTitle();

        double newRating = (movieVotesPrevious * movie.getRating() + voteRequest.getRating())/movieVotes;
        movie.setVotes(movieVotes);
        movie.setRating(newRating);
        movieRepository.save(movie);

        return new VoteResponse(movieVotes, newRating, movieTitle);
    }

    @Override
    public String userSeeMovie(long idMovie, long idUser) {
        // Recuperamos la película que vamos a ver
        Movie movie = movieRepository.findById(idMovie)
                .orElseThrow(() -> new MovieNotFoundException(idMovie));

        // Inicializamos la lista
        if (movie.getUsersSaw() == null){
            movie.setUsersSaw(new ArrayList<>());
        }

        // Evitamos duplicados en la película
        if  (movie.getUsersSaw().contains(idUser)){
            return "El usuario " + idUser + " ya ha marcado como vista la película" + movie.getTitle();
        }
        // Añadimos el usuario a la List usersSaw y guardamos cambios
        movie.getUsersSaw().add(idUser);
        movieRepository.save(movie);
        return "El usuario " + idUser + " ha marcado como vista la película " + movie.getTitle();
    }

    //Método consulta microservicio Users
    @Override
    public List<UserDTO> findUsersByMovie(String token, Long idMovie) {
        // Consultamos la Movie
        Movie movie = movieRepository
                .findById(idMovie)
                .orElseThrow(() -> new MovieNotFoundException(idMovie));
        // Controlamos que la List esté vacía, por lo tanto, sea nula
        // en caso de estar vacía inicializamos una List
        if (movie.getUsersSaw() == null || movie.getUsersSaw().isEmpty()){
                return new ArrayList<>();
        }
        // Obtenemos los usuarios que tenemos guardado en la lista
        List<Long> usersId = movie.getUsersSaw();
        // Creamos una Lista de UsersDTO donde guardar cada User
        List<UserDTO> users = new ArrayList<>();
        // Iteramos cada id dentro de la lista y los recuperamos en la lista UsersDTO
        for (Long userId : usersId) {
            // Usamos el método que tenemos dentro del Feign Client
            UserDTO user = userClient.getUser(token, userId);
            // Añadimos dentro de la lista cada UserDTO
            users.add(user);
        }
        return users;
    }
    

}
