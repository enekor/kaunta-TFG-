package com.kunta.kaunta_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegiterDTO {
    
    private String user;
    private String password;
}
