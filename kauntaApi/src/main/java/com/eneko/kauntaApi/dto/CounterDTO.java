package com.eneko.kauntaApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterDTO {

    private long id;

    private String name;

    private String description;

    private int count;

    private String img;

    private long group;
}
