package com.kunta.kaunta_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditContadorDTO {
    
    private long id;

    private String name;

    private int counter;

    private String descripcion;
}
