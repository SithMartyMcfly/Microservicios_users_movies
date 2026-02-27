package com.usersproject.users.service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.usersproject.users.DTO.UserDTO;
import com.usersproject.users.entity.User;
import com.usersproject.users.exceptions.BadRequestException;
import com.usersproject.users.exceptions.UserNotFoundException;
import com.usersproject.users.http.request.UserCreateRequestDTO;
import com.usersproject.users.http.request.UserUpdateRequestDTO;
import com.usersproject.users.mappers.UserMapper;
import com.usersproject.users.persistence.UserRepository;
import com.usersproject.users.utils.HashPassword;
import com.usersproject.users.utils.Validator;


@Service
public class ImpUserService implements IUserservice {

    private final UserRepository userRepository;
    public ImpUserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDTO createUser(UserCreateRequestDTO request) {

        // Llamamos al método estático de la clase Validator
        // Validamos que todos los campos vayan con contenido
        Validator.fieldRequired("El nombre es obligatorio", request.getName());
        Validator.fieldRequired("El apellido es obligatorio", request.getSurname());
        Validator.fieldRequired("El Email es obligatorio", request.getEmail());
        Validator.fieldRequired("La contraseña es obligatoria", request.getPassword());
        Validator.fieldRequired("El teléfono es obligatorio", request.getPhone());

        // Comprobamos que no haya dos usuarios con mismo Email
        if (userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email " + request.getEmail() + " está en uso");
        }

        // Hasheo la contraseña
        String hashed = HashPassword.hash(request.getPassword());
        // Asigno valores a una entidad USER
        User user = new User();
        user.setNombre(request.getName());
        user.setApellido(request.getSurname());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getPhone());
        user.setPassword(hashed);
        // Guardo
        userRepository.save(user);
        // Retorno en DTO la entidad guardada
        return UserMapper.toResponseDTO(user);

    }

    @Override
    public UserDTO updateUser(UserUpdateRequestDTO request, long id) {
        User user = userRepository.findById(id)
                    .orElseThrow(()-> new UserNotFoundException(String.valueOf(id)));

            List<String> errorList = new ArrayList<>();
        
        if (request.getName() == null || request.getName().isBlank()){
            errorList.add("El campo Nombre no puede estar vacío");
        }
        if (request.getEmail() == null || request.getName().isBlank()){
            errorList.add("El campo Email no puede estar vacío");
        }
        if (request.getSurname() == null || request.getName().isBlank()){
            errorList.add("El campo Apellido no puede estar vacío");
        }


        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(request.getEmail())){
            errorList.add("Email " + request.getEmail() + " está en uso");
        }

        if (!errorList.isEmpty()){
            throw new BadRequestException(errorList);
        }

        user.setNombre(request.getName());
        user.setApellido(request.getSurname());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getPhone());

        userRepository.save(user);

        return UserMapper.toResponseDTO(user);   
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                    // Pasamos el id a String para usar la excepción que recibe String
                    .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUser(String token, long id) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // Encuentro los USERS
        List<User> userList = userRepository.findAll();

        // Paso la lista a Stream para trabajar con ella
        // retorno la List
        return userList
                .stream()
        // Mapeo los objetos de la lista a DTO's
                .map(UserMapper::toResponseDTO)
        // todo lo que he mapeado lo hago LIST
                .collect(Collectors.toList());
    }


}
