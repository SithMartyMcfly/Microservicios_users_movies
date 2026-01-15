package com.usersproyect.users.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.usersproyect.users.DTO.MovieDTO;
import com.usersproyect.users.DTO.UserDTO;
import com.usersproyect.users.entity.User;
import com.usersproyect.users.http.request.UserCreateRequestDTO;
import com.usersproyect.users.http.request.UserUpdateRequestDTO;
import com.usersproyect.users.persistence.UserRepository;
import com.usersproyect.users.service.IUserservice;
import com.usersproyect.users.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/users")
public class UserController {

    private IUserservice userService;

    public UserController (IUserservice userService){
        this.userService = userService;;
    }
    
    // Creamos un metodo que valida si el token es correcto y se 
    // lo implementamos a cada método que necesita autorización
    // NO necesito anotar el token con RequestHeader ya que este método 
    // usa su parámetro dentro de los otros métodos que SI están anotados
    private boolean validateToken (String token) {
        try {
            // Limpiamos el token que viene del LocalStorage
            if(token.startsWith("Bearer ")){
                String tokenClean = token.replaceAll("Bearer ", ""); 
                String userId = jwtUtil.getKey(tokenClean);
                return userId != null;
            } else {
                String userId = jwtUtil.getKey(token);
                return userId != null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // CURD
    @PostMapping("user")
    public UserDTO CreateUser (@RequestBody UserCreateRequestDTO request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserDTO EditUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
        return userService.updateUser(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleleteUser (@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserDTO GetUser (@PathVariable Long id){
            return userService.getUser(id);
        }
        
    
    @GetMapping("/users")
    public List<UserDTO> GetAllUsers () {
        return userService.getAllUsers();
    }

    

    @GetMapping("/user/by-movie/{idMovie}")
    public List<UserDTO> findUserByMovie(@PathVariable Long idMovie) {
        return userService.getUsersByMovie(idMovie);
    }
    

}
