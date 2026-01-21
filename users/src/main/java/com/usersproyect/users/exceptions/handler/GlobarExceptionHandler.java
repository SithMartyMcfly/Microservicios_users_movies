package com.usersproyect.users.exceptions.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.usersproyect.users.exceptions.BadRequestException;
import com.usersproyect.users.exceptions.UnauthorizedException;
import com.usersproyect.users.exceptions.UserNotFoundException;
import com.usersproyect.users.exceptions.response.ErrorResponse;


@ControllerAdvice
public class GlobarExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound (UserNotFoundException ex,
                                                                WebRequest request){
                // Usamos el constructor de la Clase Error response para
                // crear nuestra respuesta
            ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(), 
            LocalDateTime.now(),
            "Not Found",
            ex.getMessage(),
            request.getDescription(false));

                // Devolvemos la Entidad con el estado y en el cuerpo enviamos
                // el objeto de error que hemos creado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthoried (UnauthorizedException ex,
                                                                WebRequest request){
                ErrorResponse error = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    LocalDateTime.now(),
                    "Usuario no autorizado",
                    ex.getMessage(),
                    request.getDescription(false));

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException (BadRequestException ex,
                                                                    WebRequest request){
                ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now(),
                    "Bad Request",
                    ex.getMessage(),
                    request.getDescription(false));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
