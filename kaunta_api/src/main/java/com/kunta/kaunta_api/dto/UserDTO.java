package com.kunta.kaunta_api.dto;

import java.util.List;

import com.kunta.kaunta_api.model.Grupo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private long id;
    private String username;
    private List<Grupo> grupos;
}
