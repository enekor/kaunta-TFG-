package com.kunta.kaunta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.User;

public interface UserRepository extends JpaRepository<User,Long>  {

    /**
     * devuelve el usuario que tenga dicho nombre de usuario
     * @param name nombre de usuario a buscar
     * @return el usuario con ese nombre
     */
    User findByUsername(String name);

    /**
     * si existe usuario con la id
     * @param name nombre a buscar
     * @return si existe o no usuaro con ese nombre
     */
    boolean existsByUsername(String name);
    
}
