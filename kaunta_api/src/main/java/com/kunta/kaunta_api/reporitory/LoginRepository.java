package com.kunta.kaunta_api.reporitory;

import com.kunta.kaunta_api.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {

    /**
     * devuelve la clase login que contenga el token
     * @param token el token filtro
     * @return el login correspondiente al token
     */
    Login findByToken(String token);

    /**
     * devuelve la clase login que contenga la id del usuario
     * @param id la id del login
     * @return la clase login correspondiente al login
     */
    Login findByIdUsuario(long id);

    /**
     * si existe o no un objeto login asociada al usuario
     * @param id id del login
     * @return si existe o no una sesion con esa id
     */
    boolean existsByIdUsuario(long id);

    /**
     * si existe o no un objeto login asociado al token
     * @param token el token filtro
     * @return si existe o no una sesion con ese token
     */
    boolean existsByToken(String token);
}
