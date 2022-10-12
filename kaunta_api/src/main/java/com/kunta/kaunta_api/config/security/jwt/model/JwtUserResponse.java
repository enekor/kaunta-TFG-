package com.kunta.kaunta_api.config.security.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import com.kunta.kaunta_api.dto.UserDTO;
import com.kunta.kaunta_api.model.Grupo;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UserDTO {
    @NotNull(message = "El token no puede ser nulo")
    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(long id,String username,List<Grupo> grupos, String token) {
        super(id, username,grupos);
        this.token = token;
    }
}
