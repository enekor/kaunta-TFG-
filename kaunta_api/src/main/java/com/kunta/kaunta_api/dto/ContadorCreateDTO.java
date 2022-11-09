package com.kunta.kaunta_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContadorCreateDTO {
    private String name;

    private String description;

    private String image;

    private int count;

    private long group;
}
