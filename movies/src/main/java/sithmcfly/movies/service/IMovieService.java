package sithmcfly.movies.service;

import java.util.List;

import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.http.request.MovieRequestDTO;
import sithmcfly.movies.http.request.VoteRequest;
import sithmcfly.movies.http.response.MovieResponseCreateDTO;
import sithmcfly.movies.http.response.MovieResponseUpdateDTO;
//import sithmcfly.movies.http.response.UsersByMovieResponse;
import sithmcfly.movies.http.response.VoteResponse;

public interface IMovieService {
    List<MovieDTO> findAll();
    MovieDTO findById(Long id);
    MovieResponseCreateDTO createMovie(MovieRequestDTO movie);
    MovieResponseUpdateDTO editMovie(MovieRequestDTO movieUpdate, Long id);
    void deleteMovie (Long id);
    VoteResponse voteMovie (VoteRequest voteRequest, Long id);
    //Con este método consultamos los Usuarios que vieron la Movie
    //UsersByMovieResponse findUsersByMovie (Long idMovie);
}