package com.kunta.kaunta_api.utils;

import com.kunta.kaunta_api.model.Login;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class IsAfterCheck {

    public boolean isAfter(Login login){
        return LocalDateTime.now().isAfter(login.getExpireDate());
    }
}
