package com.userproject.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usersproject.users.DTO.UserDTO;
import com.usersproject.users.entity.User;
import com.usersproject.users.exceptions.BadRequestException;
import com.usersproject.users.exceptions.UserNotFoundException;
import com.usersproject.users.http.request.UserCreateRequestDTO;
import com.usersproject.users.http.request.UserUpdateRequestDTO;
import com.usersproject.users.persistence.UserRepository;
import com.usersproject.users.service.ImpUserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ImpUserService userService;

    // TEST GETUSERS
    // Test para getUsers GETUSERS
    @Test
    void getUser_returnsUserDTO_whenUserExists (){
        // Creamos un objeto
        User user = new User();
        user.setId(1L);
        user.setNombre("Antonio");
        user.setApellido("Test");
        user.setEmail("test@test.com");
        user.setTelefono("1928");
        user.setPassword("1928");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Ejecutamos el metodo
        UserDTO result = userService.getUser(1L);

        // Comprobamos los resultados del metodo sean iguales al objeto creado
        assertEquals("Antonio", result.getName());
        assertEquals("Test", result.getSurname());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("1928", result.getPhone());

    }

    // Test en caso de fallo busca el mock que hemos hecho antes GETUSERS
    @Test
    void getUserThrowsException_whenUserDoesNotExists () {
        when(userRepository.findById(68L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, ()->userService.getUser(68L));
    }

    // TEST UPDATEUSERS
    // Test para updates UPDATEUSERS
    @Test
    void updateUser_updatesFieldsCorrect () {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setNombre("AntoniOld");
        existingUser.setApellido("TestOld");
        existingUser.setEmail("gutiOld@gmail.com");
        existingUser.setTelefono("1928Old");
        existingUser.setPassword("1928Old");

        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setName("AntonioNew");
        request.setSurname("TestNew");
        request.setEmail("gutiNew@gmail.com");
        request.setPhone("1928New");

        // Cuando existe el usuario
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        // Además el nuevo mail no está en uso
        when(userRepository.existsByEmail("gutiNew@gmail.com")).thenReturn(false);

        UserDTO result = userService.updateUser(request, 1L);

        assertEquals("AntonioNew", result.getName());
        assertEquals("TestNew", result.getSurname());
        assertEquals("gutiNew@gmail.com", result.getEmail());
        assertEquals("1928New", result.getPhone());
    }

    // Test mail existe en la BBDD UPDATEUSERS
    @Test
    void updateUserThrowsException_whenUserEmailExists (){
        User user = new User();
        user.setId(1L);
        user.setNombre("Antonio");
        user.setApellido("Test");
        user.setEmail("test@test.com");
        user.setTelefono("1928");
        user.setPassword("1928");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setName("Antonio");
        request.setSurname("Test");
        request.setEmail("testNew@test.com");
        request.setPhone("1928");

        when (userRepository.existsByEmail("testNew@test.com")).thenReturn(true);
        assertThrows(BadRequestException.class, ()-> userService.updateUser(request, 1L));
  
    }
    
    // Test campos en blanco UPDATEUSERS
    @Test
    void updateUserThrowsException_whenUserHasBlankFields () {
        // Usuario simulado
        User user = new User();
            user.setId(1L);
            user.setEmail("old@mail.com");

            // Le digo que usuario debe devolver, el preparado
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));

            // Preparo la modificación que hariamos en el usuario  
            UserUpdateRequestDTO request = new UserUpdateRequestDTO();
            request.setName("");
            request.setEmail("");
            request.setSurname("");

            // Ejecuto el método esperando la excepción
            // al contener tres campos vacíos debe contener tres errores   
            BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> userService.updateUser(request, 1L)
            );
        
            // Revisamos que haya tres mensajes de error y sean los siguientes
            assertEquals(3, ex.getErrors().size());
            assertTrue(ex.getErrors().contains("El campo Nombre no puede estar vacío"));
            assertTrue(ex.getErrors().contains("El campo Email no puede estar vacío"));
            assertTrue(ex.getErrors().contains("El campo Apellido no puede estar vacío"));    
    }

    //Test usuario método update no existe en la BBDD UPDATEUSERS
    @Test
    void updateUserThrowsException_whenUserDoesNotExist(){
         UserUpdateRequestDTO request = new UserUpdateRequestDTO();

        request.setName("Antonio");
        request.setSurname("Test");
        request.setEmail("testNew@test.com");
        request.setPhone("1928");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(request, 1L));
    }

    // TEST CREATEUSERS
    // Test creación de usuario CREATEUSER
    @Test
    void createUser_createsCorrectly() {
        UserCreateRequestDTO createUser = new UserCreateRequestDTO();
        createUser.setName("Antonio");
        createUser.setSurname("Gutiérrez");
        createUser.setPhone("1234");
        createUser.setPassword("1234");
        createUser.setEmail("guti@gmail.com");

        when(userRepository.existsByEmail(createUser.getEmail())).thenReturn(false);
        // save() devuelve el mismo objeto que recibe
        when(userRepository.save(any(User.class))).thenAnswer(invocation->invocation.getArgument(0));

        UserDTO result = userService.createUser(createUser);

        assertEquals("Antonio", result.getName());
        assertEquals("Gutiérrez", result.getSurname());
        assertEquals("guti@gmail.com", result.getEmail());

    }

    // Hasheo correcto de la contraseña CREATEUSER
    @Test
    void createUser_HashesPasswordCorrectly(){
       UserCreateRequestDTO createUser = new UserCreateRequestDTO();
       createUser.setName("Antonio");
       createUser.setSurname("Gutiérrez");
       createUser.setPhone("1234");
       createUser.setPassword("1234");
       createUser.setEmail("guti@gmail.com");
       
       when(userRepository.existsByEmail("guti@gmail.com")).thenReturn(false);

       when(userRepository.save(any(User.class)))
        .then(invocation -> invocation.getArgument(0));

        userService.createUser(createUser);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();

        // La contraseña no coincide con la que se introdujo y hay guardada
        assertNotEquals("1234", savedUser.getPassword());
        // La contraseña no está null
        assertNotNull(savedUser.getPassword());
        // La contraseña no esta en blanco
        assertFalse(savedUser.getPassword().isBlank());
        // La contraseña empieza con el prefijo tipico de ARGON2
        assertTrue(savedUser.getPassword().startsWith("$argon2id$"));

    }

    // Test usuario existe CREATEUSER
    @Test
    void createUserThrowsException_whenEmailAlreadyExists(){
        UserCreateRequestDTO existingUser = new UserCreateRequestDTO();
        existingUser.setName("Antonio");
        existingUser.setSurname("Gutiérrez");
        existingUser.setEmail("guti@gmail.com");
        existingUser.setPhone("1234");
        existingUser.setPassword("1234");

        when(userRepository.existsByEmail("guti@gmail.com")).thenReturn(true);

        assertThrows(BadRequestException.class, ()->userService.createUser(existingUser));

    }

    // Test campos en blanco CREATEUSER
    @Test
    void createUserThrowsException_whenFieldsAreBlank(){
        UserCreateRequestDTO existingUser = new UserCreateRequestDTO();
        existingUser.setName("");
        existingUser.setSurname("");
        existingUser.setEmail("");
        existingUser.setPhone("");
        existingUser.setPassword("");

        assertThrows(BadRequestException.class, ()-> userService.createUser(existingUser));
    }
    
}
