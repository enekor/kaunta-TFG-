package com.eneko.kauntaApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {

    private String name;

    private long user;

    private List<Long> counters;
}
