package com.eneko.kauntaApi.repository;

import com.eneko.kauntaApi.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
    Page<Group> findAllByActive(Pageable pageable, boolean active);
}
