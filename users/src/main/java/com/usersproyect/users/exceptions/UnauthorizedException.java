package com.usersproyect.users.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (){
        super("Usuario o contraseña incorrecta");
    }

}
