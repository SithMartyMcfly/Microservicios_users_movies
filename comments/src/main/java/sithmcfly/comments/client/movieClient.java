package sithmcfly.comments.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import sithmcfly.comments.DTO.MovieDTO;

@FeignClient(name="microservice-movie", url="http://localhost:8092")
public interface movieClient {
    @GetMapping("/api/movies/{id}")
    MovieDTO getMovie(@PathVariable long id);
}
