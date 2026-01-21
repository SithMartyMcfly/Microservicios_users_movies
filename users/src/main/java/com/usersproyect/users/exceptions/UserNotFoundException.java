package com.usersproyect.users.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String user){
        super("Usuario " + user + " no encontrado");
    }
}
