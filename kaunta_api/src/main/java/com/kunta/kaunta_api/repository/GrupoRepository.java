package com.kunta.kaunta_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.model.User;

public interface GrupoRepository extends JpaRepository<Grupo,Long> {

    /**
     * devuelve todos los grupos de un usuario
     * @param user usuario filtro
     * @return listado de grupos
     */
    List<Grupo> findAllByUser(User user);

    /**
     * devuelve todos los grupos de un usuarioy que cumplan los
     * requisitos de visibilidad de la base de datos
     * @param user usuario filtro
     * @param activo si esta activo o no
     * @return listado de grupos
     */
    List<Grupo> findAllByUserAndActivo(User user,boolean activo);
}
