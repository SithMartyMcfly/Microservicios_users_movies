package sithmcfly.movies.mappers;

import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.http.response.MovieResponseCreateDTO;
import sithmcfly.movies.http.response.MovieResponseUpdateDTO;

// Mapeamos la Entidad para devolver DTO
public class MovieMapper {
    
    // Mapea a MovieDTO
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

    // Mapea a MovieResponseCreateDTO
    public static MovieResponseCreateDTO toCreateResponseDTO (Movie movie){
        MovieResponseCreateDTO dto = new MovieResponseCreateDTO();

        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setYear(movie.getYear());
        dto.setDescription(movie.getDescription());
        dto.setImageUrl(movie.getImageUrl());

        return dto;  
    }

    // Mapea a MovieResponseUpdateDTO
    public static MovieResponseUpdateDTO toUpdateResponseDTO (Movie movie){
        MovieResponseUpdateDTO dto = new MovieResponseUpdateDTO();

        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setYear(movie.getYear());
        dto.setDescription(movie.getDescription());
        dto.setImageUrl(movie.getImageUrl());

        return dto;  
    }
}
