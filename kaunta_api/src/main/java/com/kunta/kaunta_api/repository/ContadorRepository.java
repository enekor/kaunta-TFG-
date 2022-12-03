package com.kunta.kaunta_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.Contador;
import com.kunta.kaunta_api.model.Grupo;

public interface ContadorRepository extends JpaRepository<Contador,Long>{

    /**
     *  devuelve todos los contadores de un grupo y que
     *  cumplan los requisitos de visibilidad de la base de datos
     * @param group filtro grupo
     * @param active si es activo o no
     * @return lista de contadores
     */
    List<Contador> findAllByGroupAndActive(Grupo group, boolean active);
}
