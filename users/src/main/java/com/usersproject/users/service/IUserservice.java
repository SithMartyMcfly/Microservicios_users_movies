package com.usersproject.users.service;

import java.util.List;

import com.usersproject.users.DTO.LoginUserDto;
import com.usersproject.users.DTO.UserDTO;
import com.usersproject.users.http.request.UserCreateRequestDTO;
import com.usersproject.users.http.request.UserUpdateRequestDTO;

public interface IUserservice {

    String login(LoginUserDto request);
    UserDTO createUser (UserCreateRequestDTO request);
    UserDTO updateUser (UserUpdateRequestDTO request, Long id);
    void deleteUser (Long id);
    UserDTO getUser (Long id);
    List<UserDTO> getAllUsers ();
    //Conexion con microservicio Movie
    //List<UserDTO> getUsersByMovie (Long idMovie);

}
