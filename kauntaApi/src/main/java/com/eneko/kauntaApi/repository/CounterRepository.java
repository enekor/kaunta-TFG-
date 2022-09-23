package com.eneko.kauntaApi.repository;

import com.eneko.kauntaApi.model.Counter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter,Long> {
    List<Counter> findAllByActive(boolean active);
}
