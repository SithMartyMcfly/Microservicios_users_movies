package sithmcfly.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//importamos la Entidad
import sithmcfly.movies.models.Movie;


//Extiende de JpaRepository, y nos pide que le introduzcamos 
// la entidad contra la que trabaja y el tipo de dato de su clave primaria
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
