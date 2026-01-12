package sithmcfly.movies.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException (Long id){
        super ("Película " + id + " no encontrada" );
    }
}
