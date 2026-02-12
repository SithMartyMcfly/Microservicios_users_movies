package sithmcfly.movies.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.exception.MovieNotFoundException;
import sithmcfly.movies.persistence.MovieRepository;


@ExtendWith(MockitoExtension.class)
class MoviesServiceTests {

	@Mock
	private MovieRepository movieRepository;

	@InjectMocks
	private ImpMovieService movieService;

	// TEST GETMOVIE
	// Test para getMovie
	@Test
	void getMovie_retunrsMovieDTO_whenMovieExists(){
		//Creamos un objeto Movie
		Movie movie = new Movie();
		movie.setId(1L);
		movie.setTitle("TestMovie");
		movie.setDirector("TestDirector");
		movie.setYear(2000);
		movie.setDescription("TestDescription");
		movie.setRating(8.0);
		movie.setVotes(1);
		movie.setImageUrl("TestUrl");

		when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

		//Ejecutamos el método del movieService
		MovieDTO result = movieService.getMovie(1L);

		//Comprobamos el resultado del objeto que devuelve nuestro método
		assertEquals("TestMovie", result.getTitle());
		assertEquals("TestDirector", result.getDirector());
		assertEquals("TestDescription", result.getDescription());
		assertEquals(8.0, result.getRating());
		assertEquals("TestUrl", result.getImageUrl());
	}

	// Test salta excepción al no encontrar Movie
	@Test
	void getMovieThrowsException_whenMovieDoesNotExists(){
		when(movieRepository.findById(68L)).thenReturn(Optional.empty());
		assertThrows(MovieNotFoundException.class, ()->movieService.getMovie(68L));
	}



	

	

}
