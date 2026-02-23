package com.usersproject.users.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        ;
    }

    // CURD
    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario usando un UserCreateRequestDTO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos")
    })
    @PostMapping
    public UserDTO createUser(@RequestBody UserCreateRequestDTO request) {
        return userService.createUser(request);
    }

    @Operation(summary = "Editar un usuario", description = "Edita un usuario que ya se encuentra en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente"),
            @ApiResponse(responseCode = "400", description = "Email está en uso en el sistema"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })

    @PutMapping("/{id}")
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
        return userService.updateUser(request, id);
    }

    @Operation(summary = "Borrar un usuario", description = "Borra un usuario pasando su Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario borrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Encuentra un usuario", description = "Encuentra un usuario pasando su Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @Operation(summary = "Listar usuarios", description = "Encuentra los usuarios contenidos en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
    })
    @GetMapping("/list")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


}
