package sithmcfly.movies.client;

import java.util.List;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestHeader;
import sithmcfly.movies.DTO.UserDTO;
import sithmcfly.movies.http.response.UsersByMovieResponse;

@FeignClient(name="microservice-user", url="http://localhost:8091")
public interface UserClient {
    // Usamos mismo Mapping y misma firma que usa el getUsers de Microservicio Users
    @GetMapping("/api/users/{id}")
    UserDTO getUser(@RequestHeader("Authorization") String token, @PathVariable Long id);

}
