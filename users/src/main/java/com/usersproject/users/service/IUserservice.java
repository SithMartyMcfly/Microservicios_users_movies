package com.usersproject.users.service;

import java.util.List;

import com.usersproject.users.DTO.UserDTO;
import com.usersproject.users.http.request.UserCreateRequestDTO;
import com.usersproject.users.http.request.UserUpdateRequestDTO;

public interface IUserservice {

    UserDTO createUser (UserCreateRequestDTO request);
    UserDTO updateUser (UserUpdateRequestDTO request, long id);
    void deleteUser (long id);
    UserDTO getUser (String token, long id);
    List<UserDTO> getAllUsers ();
}
