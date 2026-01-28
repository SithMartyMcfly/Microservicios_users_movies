package com.usersproject.users.exceptions;

import java.util.List;


public class BadRequestException extends RuntimeException {
    
    // Vamos a sobre cargar esta excepcion para que acepte List<String>
    private final List<String> errors;
    
    public BadRequestException (String message){
        super(message);
        this.errors = List.of(message);
    }

    public BadRequestException(List<String> errors){
        super("Errores de validación");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
