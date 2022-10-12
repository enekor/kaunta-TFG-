package com.kunta.kaunta_api.config.security.jwt.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "El campo username no puede estar vacío")
    private String name;
    @NotBlank(message = "El campo password no puede estar vacío")
    private String password;
}
