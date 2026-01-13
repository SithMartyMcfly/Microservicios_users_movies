package sithmcfly.movies.service;

import java.util.List;

import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.http.request.VoteRequest;
import sithmcfly.movies.http.response.UsersByMovieResponse;
import sithmcfly.movies.http.response.VoteResponse;

public interface IMovieService {
    List<Movie> findAll();
    Movie findById(Long id);
    Movie createMovie(Movie movie);
    void deleteMovie (Long id);
    Movie editMovie(Long id, Movie updatMovie);
    VoteResponse voteMovie (Long id, VoteRequest voteRequest);
    //Con este método consultamos los Usuarios que vieron la Movie
    UsersByMovieResponse findUsersByMovie (Long idMovie);
}