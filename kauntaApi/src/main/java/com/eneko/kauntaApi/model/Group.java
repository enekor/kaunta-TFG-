package com.eneko.kauntaApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Counter> counters;

    private boolean active;
}
