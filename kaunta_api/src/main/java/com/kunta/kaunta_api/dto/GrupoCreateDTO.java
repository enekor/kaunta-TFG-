package com.kunta.kaunta_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoCreateDTO {
    
    private long id;

    private String nombre;

    private long user;
}
