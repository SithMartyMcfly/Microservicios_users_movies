package sithmcfly.movies.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.exception.MovieNotFoundException;
import sithmcfly.movies.http.request.MovieRequestDTO;
import sithmcfly.movies.http.response.MovieResponseCreateDTO;
import sithmcfly.movies.http.response.MovieResponseUpdateDTO;
import sithmcfly.movies.mappers.MovieMapper;
import sithmcfly.movies.persistence.MovieRepository;


@ExtendWith(MockitoExtension.class)
class MoviesServiceTests {

	@Mock
    // Mock del repositorio
	private MovieRepository movieRepository;
    // Servicio que vamos a testear
	private ImpMovieService movieService;
    // Mock para métodos estáticos del Mapper
    private MockedStatic<MovieMapper> movieMapperMock;


    @BeforeEach
    void setup(){
        // Creamos el servicio real, pero con repo mockeado
        movieService = new ImpMovieService(movieRepository);
        // Activamos el mock estático del MovieMapper, podemos realizar llamadas a los Mappers
        movieMapperMock = Mockito.mockStatic(MovieMapper.class);
    }

    @AfterEach
    void tearDown() {
        // Cerramos el mock estático
        movieMapperMock.close();
    }

    @Test
    void getAllMovies_ReturnsMappedList() {
        // Arrange (preparamos el escenario para test)
        // Creamos dos entidades Movie simuladas
        Movie movie = new Movie();
        Movie movie1 = new Movie();

        // Creamos el DTI que uqeremos que devuelva el Mapper
        MovieDTO movieDTO = new MovieDTO();
        MovieDTO movieDTO1 = new MovieDTO();

        // Mockeamos que el repo devuelve dos entidades
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie, movie1));

        // Mockeamos el mapeador
        movieMapperMock.when(() -> MovieMapper.toResponseDTO(movie)).thenReturn(movieDTO);
        movieMapperMock.when(() -> MovieMapper.toResponseDTO(movie1)).thenReturn(movieDTO1);

        // Act (ejecutamos el método real del servicio)
        List<MovieDTO> result = movieService.getAllMovies();

        // Assert (comprobamos que el resultado es el esperado)
        // el resultado contiene dos indices
        assertEquals(2, result.size());
        // Contiene el DTO mapeado
        assertTrue(result.contains(movieDTO));
        assertTrue(result.contains(movieDTO1));
    }

    @Test
    void getAllMovies_ReturnsEmpty_NoMoviesFound(){
        //Arrange
        // Simulamos que el repo no devuelve nada
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());

        //Act
        List<MovieDTO> result = movieService.getAllMovies();

        //Assert
        // La lista está vacía
        assertTrue(result.isEmpty());
        // Se llama una vez al repositorio
        verify(movieRepository, times(1)).findAll();

    }
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

        // Creamos un MovieDTO
        MovieDTO dto = new MovieDTO();
        dto.setTitle("TestMovie");
        dto.setDirector("TestDirector");
        dto.setDescription("TestDescription");
        dto.setRating(8.0);
        dto.setImageUrl("TestUrl");

        // Mockeamos el repositorio
		when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        // Mapeamos el resultado
        movieMapperMock.when(()-> MovieMapper.toResponseDTO(movie)).thenReturn(dto);

		//Ejecutamos método del movieService
		MovieDTO result = movieService.getMovie(1L);

		//Comprobamos el resultado del objeto que devuelve nuestro método
		assertEquals("TestMovie", result.getTitle());
		assertEquals("TestDirector", result.getDirector());
		assertEquals("TestDescription", result.getDescription());
		assertEquals(8.0, result.getRating());
		assertEquals("TestUrl", result.getImageUrl());

        verify(movieRepository, times(1)).findById(1L);
	}

	// Test salta excepción al no encontrar Movie
	@Test
	void getMovieThrowsException_whenMovieDoesNotExists(){
		when(movieRepository.findById(68L)).thenReturn(Optional.empty());
		assertThrows(MovieNotFoundException.class, ()->movieService.getMovie(68L));
        verify(movieRepository, times(1)).findById(68L);
	}

    // TEST CREATE
    @Test
    void createMovie_returnsCreatedMovieDTO(){
        // DTO que recibe el método create
        MovieRequestDTO request = new MovieRequestDTO();
        request.setTitle("NewMovie");
        request.setDirector("NewDirector");
        request.setDescription(("NewDescription"));
        request.setYear(2025);
        request.setImageUrl("NewPicture");

        // Entidad que debería generarse
        Movie entity = new Movie();
        entity.setTitle("NewMovie");
        entity.setDirector("NewDirector");
        entity.setDescription(("NewDescription"));
        entity.setYear(2025);
        entity.setImageUrl("NewPicture");
        
        // DTO que devolverá el Mapper
        MovieResponseCreateDTO responseDTO = new MovieResponseCreateDTO();
        responseDTO.setTitle("NewMovie");
        responseDTO.setDirector("NewDirector");
        responseDTO.setDescription(("NewDescription"));
        responseDTO.setYear(2025);
        responseDTO.setImageUrl("NewPicture");
        
        // Mock del mapper estático, pasamos request a entity
        movieMapperMock.when(()->MovieMapper.toEntity(request)).thenReturn(entity);
        // Mock del repositorio save
        when(movieRepository.save(entity)).thenReturn(entity);
        // Mock del mapper que devuelve el create
        movieMapperMock.when(()->MovieMapper.toMovieCreateResponseDTO(entity)).thenReturn(responseDTO);

        // Act, ejecutamos el método del servicio
        MovieResponseCreateDTO result = movieService.createMovie(request);

        // Assert comprobar resultado esperado
        assertEquals("NewMovie", result.getTitle());
        assertEquals("NewDirector", result.getDirector());
        assertEquals("NewDescription", result.getDescription());
        assertEquals(2025, result.getYear());
        assertEquals("NewPicture", result.getImageUrl());

        verify(movieRepository, times(1)).save(entity);
    }

    // TEST UPDATE
    @Test
    void editMovie_updatesMovieAndReturnsUpdatedDTO (){
        //Arrange preparamos datos y mockeo

        // Datos que llegan del servicio que recibirá nuestra entidad
        MovieRequestDTO request = new MovieRequestDTO();
        request.setTitle("UpdatedTitle");
        request.setDirector("UpdatedDirector");
        request.setYear(2026);
        request.setDescription("UpdatedDescription");
        request.setImageUrl("UpdatedUrl");

        // Película de la BBDD y que vamos a cambiar
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("OldMovie");
        movie.setDirector("OldDirector");
        movie.setYear(2024);
        movie.setDescription("OldDescription");
        movie.setImageUrl("OldImage");

        //DTO que devuelve el metodo update al pasar por mapper
        MovieResponseUpdateDTO updatedDTO = new MovieResponseUpdateDTO();
        updatedDTO.setTitle("UpdatedTitle");
        updatedDTO.setDirector("UpdatedDirector");
        updatedDTO.setYear(2026);
        updatedDTO.setDescription("UpdatedDescription");
        updatedDTO.setImageUrl("UpdatedUrl");

        //Mock película existe
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        //Mock salvamos la película
        when(movieRepository.save(movie)).thenReturn(movie);
        //Mock del mapper
        movieMapperMock.when(()->MovieMapper.toUpdateResponseDTO(movie)).thenReturn(updatedDTO);
        //Act ejecutamos el método real
        MovieResponseUpdateDTO result = movieService.editMovie(request, 1L);

        //Assert comprobación de resultados
        assertEquals("UpdatedTitle", movie.getTitle());
        assertEquals("UpdatedDirector", movie.getDirector());
        assertEquals(2026, movie.getYear());
        assertEquals("UpdatedDescription", movie.getDescription());
        assertEquals("UpdatedUrl", movie.getImageUrl());

        //Comprobamos que el resultado es del DTO esperado
        assertEquals("UpdatedTitle", result.getTitle());
        assertEquals("UpdatedDirector", result.getDirector());
        assertEquals(2026, result.getYear());
        assertEquals("UpdatedDescription", result.getDescription());
        assertEquals("UpdatedUrl", result.getImageUrl());

         verify(movieRepository, times(1)).findById(1L);
         verify(movieRepository, times(1)).save(movie);

    }

    @Test
    void editMovie_throwsException_whenMovieNotExists(){
        // Simulamos que no existe la película
        when(movieRepository.findById(68L)).thenReturn(Optional.empty());
        // Act + Assert debe lanzar NotFoundException
        assertThrows(MovieNotFoundException.class, ()-> movieService.editMovie(new MovieRequestDTO(), 68L));

        verify(movieRepository, times(1)).findById(68L);
        verify(movieRepository, never()).save(any());

    }

}
