package com.kunta.kaunta_api.dto;

import com.kunta.kaunta_api.model.Contador;
import com.kunta.kaunta_api.model.Grupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountersOutDto {

    private List<Contador> contadores;
}
