package com.kunta.kaunta_api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grupo {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    private String nombre;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    private boolean activo;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Contador> counters;
}
