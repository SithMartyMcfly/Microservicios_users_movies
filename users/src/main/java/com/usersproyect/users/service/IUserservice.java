package com.usersproyect.users.service;

import java.util.List;

import com.usersproyect.users.DTO.UserDTO;
import com.usersproyect.users.http.request.LoginRequestDTO;
import com.usersproyect.users.http.request.UserCreateRequestDTO;
import com.usersproyect.users.http.request.UserUpdateRequestDTO;

public interface IUserservice {

    String login(LoginRequestDTO request);
    UserDTO createUser (UserCreateRequestDTO request);
    UserDTO updateUser (UserUpdateRequestDTO request, Long id);
    void deleteUser (Long id);
    UserDTO getUser (Long id);
    List<UserDTO> getAllUsers ();
    //Conexion con microservicio Movie
    List<UserDTO> getUsersByMovie (Long idMovie);

}
