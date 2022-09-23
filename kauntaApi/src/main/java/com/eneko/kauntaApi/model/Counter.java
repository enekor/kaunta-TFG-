package com.eneko.kauntaApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private int count;

    private String img;

    @ManyToOne
    private Group group;

    boolean active;
}
