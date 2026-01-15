package com.usersproyect.users.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.usersproyect.users.DTO.UserDTO;
import com.usersproyect.users.entity.User;
import com.usersproyect.users.http.request.LoginRequestDTO;
import com.usersproyect.users.http.request.UserCreateRequestDTO;
import com.usersproyect.users.http.request.UserUpdateRequestDTO;
import com.usersproyect.users.persistence.UserRepository;
import com.usersproyect.users.mappers.UserMapper;
import com.usersproyect.users.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import jakarta.persistence.EntityManager;

@Service
public class ImpUserService implements IUserservice {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final EntityManager entityManager;
    public ImpUserService (UserRepository userRepository, JWTUtil jwtUtil, EntityManager entityManager){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.entityManager = entityManager;
    }

    @Override
    public String login(LoginRequestDTO request) {
        //Buscamos el usuario cuyo mail coincida
        // con lo que envía la request
       List <User> userList = entityManager.createQuery("From User u where u.email = :email", User.class)
                    .setParameter("email", request.getEmail())
                    .getResultList();

                    if (userList.isEmpty()){
                        return null;
                    }
        User user = userList.get(0);
        String passwordHashed = userList.get(0).getPassword();
        char[] passwordChars = request.getPassword().toCharArray();
        Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);

        if(!argon2.verify(passwordHashed, passwordChars)){
            return null;
        } else {
            return jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
        }
    }

    @Override
    public UserDTO createUser(UserCreateRequestDTO request) {
        // Hasheo la contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        String hashed = argon2.hash(1, 1024, 1, request.getPassword().toCharArray());
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
    public UserDTO updateUser(UserUpdateRequestDTO request, Long id) {
        User user = userRepository.findById(id)
        //TODO excepciones
                    .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        user.setNombre(request.getName());
        user.setApellido(request.getSurname());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getPhone());

        userRepository.save(user);

        return UserMapper.toResponseDTO(user);   
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                    // TODO excepciones
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                    // TODO excepciones
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // Encuentro los USERS
        List<User> userList = userRepository.findAll();

        // Paso la lista a Stream para trabajar con ella
        List<UserDTO> userMappedList = userList.stream()
        // Mapeo los objetos de la lista a DTO's
        .map(UserMapper::toResponseDTO)
        // todo lo que he mapeado lo hago LIST
        .collect(Collectors.toList()); 
        // retorno la List
        return userMappedList;
    }


    @Override
    public List<UserDTO> getUsersByMovie(Long idMovie) {
        List<User> userList = userRepository.getUsersByMovie(idMovie);

        return userList.stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
