package sithmcfly.movies.http.response;

import java.util.List;

import sithmcfly.movies.DTO.UserDTO;

public class UsersByMovieResponse {
    private String movieName;
    private String directorName;
    private List<UserDTO> userList;


    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getDirectorNameString() {
        return directorName;
    }
    public void setDirectorNameString(String directorNameString) {
        this.directorName = directorNameString;
    }
    public List<UserDTO> getUserList() {
        return userList;
    }
    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }
    public UsersByMovieResponse(String movieName, String directorNameString, List<UserDTO> userList) {
        this.movieName = movieName;
        this.directorName = directorNameString;
        this.userList = userList;
    }
    public UsersByMovieResponse() {
    }

    

    
}
