package com.kunta.kaunta_api.utils;

import java.util.Base64;

public class PasswordEncoder {

    private static PasswordEncoder instance = null;
    private PasswordEncoder(){}

    public static PasswordEncoder getInstance(){
        if(instance==null){
            instance = new PasswordEncoder();
        }
        return instance;
    }

    public String encodeToken(String token){
        byte[] bytes = token.getBytes();
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }

    public String decodeToken(String token ){
        byte[] bytes = token.getBytes();
        byte[] decodedBytes = Base64.getDecoder().decode(bytes);
        String decoded = new String(decodedBytes);
        return decoded;
    }
}
