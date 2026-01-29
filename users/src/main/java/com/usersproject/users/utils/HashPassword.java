package com.usersproject.users.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HashPassword {
    private static final Argon2 argon2 =
        Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        public static String hash(String passRaw){
            char[] passChars = passRaw.toCharArray();
            return argon2.hash(1, 1024, 1, passChars);
        }

}
