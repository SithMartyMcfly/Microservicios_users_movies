package sithmcfly.movies.mappers;

import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.entities.Movie;

// Mapeamos la Entidad para devolver DTO
public class MovieMapper {
    public static MovieDTO toResponseDTO (Movie movie){
        MovieDTO dto = new MovieDTO();

        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setYear(movie.getYear());
        dto.setDescription(movie.getDescription());
        dto.setImageUrl(movie.getImageUrl());
        dto.setRating(movie.getRating());

        return dto;  
    }

}
