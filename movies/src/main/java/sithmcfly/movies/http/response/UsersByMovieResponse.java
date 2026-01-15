package sithmcfly.movies.http.response;

import java.util.List;

import sithmcfly.movies.DTO.UserDTO;

public class UsersByMovieResponse {
    private String movieName;
    private String director;
    private List<UserDTO> userList;


    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public List<UserDTO> getUserList() {
        return userList;
    }
    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }
    public UsersByMovieResponse(String movieName, String director, List<UserDTO> userList) {
        this.movieName = movieName;
        this.director = director;
        this.userList = userList;
    }
    public UsersByMovieResponse() {
    }

    

    
}
