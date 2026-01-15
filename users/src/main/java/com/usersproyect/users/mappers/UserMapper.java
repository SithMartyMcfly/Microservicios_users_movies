package com.usersproyect.users.mappers;

import com.usersproyect.users.entity.User;
import com.usersproyect.users.DTO.UserDTO;

public class UserMapper {
    public static UserDTO toResponseDTO (User user){

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getNombre());
        dto.setSurname(user.getApellido());
        dto.setEmail(user.getEmail());

        return dto;
    }

}
