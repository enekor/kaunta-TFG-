package com.kunta.kaunta_api.reporitory;

import com.kunta.kaunta_api.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {

    public Login findByToken(String token);

    public Login findByIdUsuario(long id);

    public boolean existsByIdUsuario(long id);

    public boolean existsByToken(String token);
}
