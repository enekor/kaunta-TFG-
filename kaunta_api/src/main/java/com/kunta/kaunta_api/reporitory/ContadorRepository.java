package com.kunta.kaunta_api.reporitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.Contador;

public interface ContadorRepository extends JpaRepository<Contador,Long>{
    
}
