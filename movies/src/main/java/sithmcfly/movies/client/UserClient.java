package sithmcfly.movies.client;

import java.util.List;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sithmcfly.movies.DTO.UserDTO;
import sithmcfly.movies.http.response.UsersByMovieResponse;

//@FeignClient(name="microservice-user", url="localhost:8091/api")
public interface UserClient {
    @GetMapping("app/user/by-movie/{idMovie}")
    List<UserDTO> findUserByMovie(@PathVariable Long idMovie);
    UsersByMovieResponse findUsersByMovieResponse (Long idMovie);
}
