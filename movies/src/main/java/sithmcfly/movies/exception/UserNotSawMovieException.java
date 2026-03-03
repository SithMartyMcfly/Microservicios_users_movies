package sithmcfly.movies.exception;

public class UserNotSawMovieException extends RuntimeException {
    public UserNotSawMovieException(Long id) {
        super("El usuario " + id + " no ha visto la película");
    }
}
