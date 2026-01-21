package com.usersproject.users.utils;

import com.usersproject.users.exceptions.BadRequestException;

public class Validator {
    public static void fieldRequired(String message, String value) {
        if (value.isBlank()){
            throw new BadRequestException(message);
        }
    }
    
}
