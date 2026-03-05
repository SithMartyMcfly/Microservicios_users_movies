package com.usersproject.users.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.usersproject.users.DTO.UserDTO;
import com.usersproject.users.http.request.UserCreateRequestDTO;
import com.usersproject.users.http.request.UserUpdateRequestDTO;
import com.usersproject.users.service.IUserservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Operaciones CRUD para gestión de Usuarios")
@RestController
@RequestMapping("api/users")
public class UserController {

    private final IUserservice userService;

    public UserController(IUserservice userService) {
        this.userService = userService;
    }

    // CURD
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    @Operation(summary = "Encuentra un usuario", description = "Encuentra un usuario pasando su Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode= "401", description = "No autorizado"),
            @ApiResponse(responseCode= "403", description = "Permisos insuficientes"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public UserDTO getUser(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return userService.getUser(token, id);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuarios", description = "Encuentra los usuarios contenidos en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode= "401", description = "No autorizado"),
            @ApiResponse(responseCode= "403", description = "Permisos insuficientes"),
    })
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario usando un UserCreateRequestDTO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode= "401", description = "No autorizado")
    })
    public UserDTO createUser(@RequestBody UserCreateRequestDTO request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    @Operation(summary = "Editar un usuario", description = "Edita un usuario que ya se encuentra en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente"),
            @ApiResponse(responseCode = "400", description = "Email está en uso en el sistema"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
        return userService.updateUser(request, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Borrar un usuario", description = "Borra un usuario pasando su Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario borrado correctamente"),
            @ApiResponse(responseCode= "401", description = "No autorizado"),
            @ApiResponse(responseCode= "403", description = "Permisos insuficientes"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")

    })
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
