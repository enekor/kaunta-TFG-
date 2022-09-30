package com.kunta.kaunta_api.reporitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo,Long> {
    
}
