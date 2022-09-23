package com.eneko.kauntaApi.repository;

import com.eneko.kauntaApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
