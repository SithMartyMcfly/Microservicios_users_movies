package com.usersproject.users.mappers;

import com.usersproject.users.DTO.UserDTO;
import com.usersproject.users.entity.User;

// Vamos a recibir un usuario y vamos a devolver el UserDTO

public class UserMapper {
    public static UserDTO toResponseDTO (User user){

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getNombre());
        dto.setSurname(user.getApellido());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getTelefono());

        return dto;
    }

}
