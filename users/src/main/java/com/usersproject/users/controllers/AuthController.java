package com.usersproject.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersproject.users.DTO.LoginUserDto;
import com.usersproject.users.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Autentificación", description = "Autentifica usuarios registrados en el sistema")
@RestController
@RequestMapping("api/")
public class AuthController {
    private final AuthService authService;

    public AuthController (AuthService authService){
        this.authService = authService;
    }
@Operation(summary = "Entrar en sistema", description = "Autentifica al usuario y le devuelve token creado por JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login ejecutado correctamente"),            
            @ApiResponse(responseCode = "400", description = "Debes pasar email y contraseña"),            
            @ApiResponse(responseCode = "401", description = "Contraseña o usuario no válido")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginUserDto request) {
        String token = authService.login(request);
        
        return ResponseEntity.ok(token);
    }
    
}
