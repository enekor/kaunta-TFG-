package com.kunta.kaunta_api.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunta.kaunta_api.model.User;

public interface UserRepository extends JpaRepository<User,Long>  {

    public User findByName(String name);
    public boolean existsByName(String name);
    
}
