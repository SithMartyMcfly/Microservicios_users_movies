package sithmcfly.movies.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import sithmcfly.movies.entities.Movie;


//Extiende de JpaRepository, y nos pide que le introduzcamos 
// la entidad contra la que trabaja y el tipo de dato de su clave primaria
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
