package sithmcfly.movies.controllers;

import java.util.List;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import sithmcfly.movies.DTO.MovieDTO;
import sithmcfly.movies.DTO.UserDTO;
import sithmcfly.movies.entities.Movie;
import sithmcfly.movies.http.request.MovieRequestDTO;
import sithmcfly.movies.http.request.VoteRequest;
import sithmcfly.movies.http.response.MovieResponseCreateDTO;
import sithmcfly.movies.http.response.MovieResponseUpdateDTO;
import sithmcfly.movies.http.response.VoteResponse;
import sithmcfly.movies.service.ImpMovieService;


@Tag(name = "Movies", description = "Operaciones CRUD de Películas y puntuación de películas")
@CrossOrigin // Para poder usar los endpoints con aplicaciones externas (Front u otras apps)
@RestController // Indica que va a ser una API REST
@RequestMapping("api/movies") // Identifica la ruta para usar los recursos
public class MovieController {

    private final ImpMovieService impMovieService;
    
    public MovieController (ImpMovieService impMovieService){
        this.impMovieService = impMovieService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar Películas", description = "Encuentra un listado de todas las películas en el sistema")
    @ApiResponse(responseCode = "200", description = "Listado de películas encontradas")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(impMovieService.getAllMovies());
    }

  
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Encuentra una película", description = "Encuentra una película pasando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description ="Película encontrada correctamente"),
        @ApiResponse(responseCode = "404", description = "La película no fue encontrada")
    })
    public ResponseEntity<MovieDTO> getMovie (@PathVariable Long id) {

        return ResponseEntity.ok(impMovieService.getMovie(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Crea una película", description = "Crea un nuevo usuario con validaciones propias de MovieRequestDTO")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Película creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos incorrectos o incompletos")
    })
    public ResponseEntity<MovieResponseCreateDTO> createMovie (@Valid @RequestBody MovieRequestDTO movie) {
        MovieResponseCreateDTO createdMovie = impMovieService.createMovie(movie);
        return ResponseEntity
                // Devolvemos el estado
                .status(HttpStatus.CREATED)
                // Devolvemos lo que hemos creado, podríamos generar un DTO para respuestas
                .body(createdMovie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Borrar una película", description = "Borra una película a partir de una Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película borrada correctamente"),
        @ApiResponse(responseCode = "404", description = "Id película no existe")
    })
    public ResponseEntity<Void> deleteMovie (@PathVariable Long id) {
        impMovieService.deleteMovie(id);
        // Un método de borrado no debe devolver nada de ahi que usemos el status NO CONTENT
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar película", description = "Permite actualizar una película pasándole un MovieRequestDTO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Película no encontrada")
    })
    public ResponseEntity<MovieResponseUpdateDTO> editMovie (@PathVariable Long id, @Valid @RequestBody MovieRequestDTO updatedMovie){
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(impMovieService.editMovie(updatedMovie, id));
    }


    @PutMapping("/{id}/vote")
    @PreAuthorize("isAuthenticated()")
    @Operation( summary = "Vota película",
                description = "Vota una película y hace un cálculo de su media con la nueva nota, devuelve el número de votos y la media de notas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película votada correctamente"),
        @ApiResponse(responseCode = "401", description = "Usuario no autentificado"),
        @ApiResponse(responseCode = "404", description = "La película no fue encontrada")
    })
    public ResponseEntity<VoteResponse> voteMovie(
            @PathVariable Long id,
            @Valid @RequestBody VoteRequest voteRequest,
            Authentication auth
    ) {
        Jwt jwt = (Jwt) auth.getPrincipal();
        Long userId = Long.parseLong(jwt.getSubject());

        return ResponseEntity.status(HttpStatus.OK)
                .body(impMovieService.voteMovie(voteRequest, id, userId));
    }

    @PutMapping("/{idMovie}/saw")
    @PreAuthorize("isAuthenticated()")
    @Operation( summary = "Película vista",
            description = "Marca una película vista por un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película marcada como vista"),
            @ApiResponse(responseCode = "401", description = "Usuario no autentificado"),
            @ApiResponse(responseCode = "404", description = "La película no fue encontrada")
    })
    public ResponseEntity<String> userSeeMovie (@PathVariable Long idMovie, Authentication auth){
        Jwt jwt = (Jwt) auth.getPrincipal();
        Long userId = Long.parseLong(jwt.getSubject());
        String email = jwt.getClaimAsString("email");
        String result = impMovieService.userSeeMovie(idMovie, userId, email);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{idMovie}/users")
    @PreAuthorize("isAuthenticated()")
    @Operation( summary = "Usuarios que han visto una película",
            description = "Vota una película y hace un cálculo de su media con la nueva nota, devuelve el número de votos y la media de notas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película marcada como vista"),
            @ApiResponse(responseCode = "404", description = "La película no fue encontrada")
    })
    public ResponseEntity<List<UserDTO>> findUsersById(
            @RequestHeader ("Authorization") String token,
            @PathVariable Long idMovie) {
        return ResponseEntity.ok(impMovieService.findUsersByMovie(token, idMovie));
    }
    
}
