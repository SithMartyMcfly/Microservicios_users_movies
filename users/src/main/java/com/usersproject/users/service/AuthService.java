package com.usersproject.users.service;

import org.springframework.stereotype.Service;

import com.usersproject.users.DTO.LoginUserDto;
import com.usersproject.users.entity.User;
import com.usersproject.users.exceptions.BadRequestException;
import com.usersproject.users.exceptions.UnauthorizedException;
import com.usersproject.users.persistence.UserRepository;
import com.usersproject.users.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public AuthService (UserRepository userRepository, JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String login(LoginUserDto request) {
        String email = request.getEmail();
        if (email == null || email.isBlank()){
            throw new BadRequestException("Debes introducir un Email");
        }
        String pass = request.getPassword();
        if (pass == null || pass.isBlank()){
            throw new BadRequestException("El password no debe estar vacío");
        }

        // Buscamos el usuario cuyo mail coincida
        // con lo que envía la request
        User user =  userRepository.findByEmail(request.getEmail())
            .orElseThrow(()-> new UnauthorizedException()); 

        String passwordHashed = user.getPassword();
        char[] passwordChars = request.getPassword().toCharArray();
        Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
        if(argon2.verify(passwordHashed, passwordChars)){
            return jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
        } else {
            throw new UnauthorizedException();
        }
    }

}
