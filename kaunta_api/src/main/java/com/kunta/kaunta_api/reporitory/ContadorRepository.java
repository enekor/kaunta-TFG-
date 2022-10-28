package com.kunta.kaunta_api.reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.Contador;
import com.kunta.kaunta_api.model.Grupo;

public interface ContadorRepository extends JpaRepository<Contador,Long>{
    public List<Contador> findAllByGroupAndActive(Grupo group, boolean active);
}
