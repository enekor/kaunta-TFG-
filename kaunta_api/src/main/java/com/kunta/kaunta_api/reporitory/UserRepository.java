package com.kunta.kaunta_api.reporitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.User;

public interface UserRepository extends JpaRepository<User,Long>  {

    public User findByUsername(String name);
    public boolean existsByUsername(String name);
    
}
