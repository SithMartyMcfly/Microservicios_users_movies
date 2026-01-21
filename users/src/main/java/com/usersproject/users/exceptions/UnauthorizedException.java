package com.usersproject.users.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (){
        super("Usuario o contraseña incorrecta");
    }

}
