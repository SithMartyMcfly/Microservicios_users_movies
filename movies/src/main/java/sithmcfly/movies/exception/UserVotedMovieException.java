package sithmcfly.movies.exception;

public class UserVotedMovieException extends RuntimeException{
    public UserVotedMovieException (Long idUser){
        super ("El usuario " + idUser + " ya ha votado la película");
    }
}
