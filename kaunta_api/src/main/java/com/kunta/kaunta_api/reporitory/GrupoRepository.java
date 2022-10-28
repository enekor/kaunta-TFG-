package com.kunta.kaunta_api.reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.model.User;

public interface GrupoRepository extends JpaRepository<Grupo,Long> {
    public List<Grupo> findAllByUser(User user);

    public List<Grupo> findAllByUserAndActivo(User user,boolean activo);
}
