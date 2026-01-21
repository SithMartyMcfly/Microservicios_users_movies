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



@RestController
@RequestMapping("api/users")
public class UserController {

    private final IUserservice userService;

    public UserController (IUserservice userService){
        this.userService = userService;;
    }
    
    // CURD
    @PostMapping
    public UserDTO createUser (@RequestBody UserCreateRequestDTO request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
        return userService.updateUser(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser (@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserDTO getUser (@PathVariable Long id){
            return userService.getUser(id);
        }
        
    
    @GetMapping
    public List<UserDTO> getAllUsers () {
        return userService.getAllUsers();
    }

    
    
    @GetMapping("/by-movie/{idMovie}")
    public List<UserDTO> findUserByMovie(@PathVariable Long idMovie) {
        return userService.getUsersByMovie(idMovie);
    }
    

}
