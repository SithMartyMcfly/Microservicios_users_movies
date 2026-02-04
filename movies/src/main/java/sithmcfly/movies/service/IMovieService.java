package sithmcfly.movies.service;

import java.util.List;

import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.http.request.VoteRequest;
import sithmcfly.movies.http.response.UsersByMovieResponse;
import sithmcfly.movies.http.response.VoteResponse;

public interface IMovieService {
    List<MovieDTO> findAll();
    MovieDTO findById(Long id);
    Movie createMovie(MovieCreateRequestDTO movie);
    void deleteMovie (Long id);
    Movie editMovie(Long id, MovieUpdateRequestDTO movieUpdate);
    VoteResponse voteMovie (Long id, VoteRequest voteRequest);
    //Con este método consultamos los Usuarios que vieron la Movie
    UsersByMovieResponse findUsersByMovie (Long idMovie);
}