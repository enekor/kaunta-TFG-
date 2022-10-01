package com.kunta.kaunta_api.utils;

import java.time.LocalDateTime;

public class Token {

    private static Token instance = null;
    private Token(){}
    public static String userName = "";

    public static Token getInstance(){
        if(instance==null){
            instance = new Token();
        }
        return instance;
    }

    public String tokenGenerator(String userName, String expireDate){
        String token ="user_name:"+userName+","+
                "expire_date:"+expireDate;
        return token;
    }

    public boolean tokenVerification(String token){
        boolean valid = false;

        String json = PasswordEncoder.getInstance().decodeToken(token);
        String[] info = json.split(",");

        String dateJSON = info[1];
        String[] dateSplit= dateJSON.split("=");
        String date = dateSplit[1];

        String nameJSON = info[0];
        String[] nameSplit = nameJSON.split("=");
        String name = nameSplit[1];



        if(LocalDateTime.now().isBefore(LocalDateTime.parse(date))){
            valid = true;
            userName = name;
        }
        else{
            valid = false;
            userName = "";
        }
        return valid;
    }
}
