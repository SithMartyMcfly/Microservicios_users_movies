package sithmcfly.movies.exception.handler;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sithmcfly.movies.exception.MovieNotFoundException;
import sithmcfly.movies.exception.UserNotSawMovieException;
import sithmcfly.movies.exception.UserVotedMovieException;
import sithmcfly.movies.exception.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ErrorResponse> handleMovieNotFound (MovieNotFoundException ex,
                                                              WebRequest request)
    {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                "Película no encontrada",
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    public ResponseEntity <ErrorResponse> handleUserNotSawMovie (UserNotSawMovieException ex,
                                                                 WebRequest request)
    {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                "El usuario ya había marcado la película como vista",
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }

    public ResponseEntity<ErrorResponse> handleUserVotedMovie (UserVotedMovieException ex,
                                                               WebRequest request)
    {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                "El usuario ya ha votado la película",
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }
}
