package com.usersproject.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersproject.users.DTO.LoginUserDto;
import com.usersproject.users.service.IUserservice;




@RestController
@RequestMapping("api/")
public class AuthController {
    private final IUserservice userservice;

    public AuthController (IUserservice userservice){
        this.userservice = userservice;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginUserDto request) {
        String token = userservice.login(request);
        
        return ResponseEntity.ok(token);
    }
    
}
