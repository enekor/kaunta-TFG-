package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.dto.GrupoCreateDTO;
import com.kunta.kaunta_api.mapper.GrupoMapper;
import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.model.User;
import com.kunta.kaunta_api.reporitory.GrupoRepository;
import com.kunta.kaunta_api.reporitory.LoginRepository;
import com.kunta.kaunta_api.reporitory.UserRepository;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = {GrupoController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GrupoControllerTest {
    /*
    @InjectMocks
    private GrupoController controller;

    @MockBean
    private LoginRepository lRepo;

    @MockBean
    private GrupoRepository repo;

    @MockBean
    private UserRepository uRepo;

    @MockBean
    private GrupoMapper mapper;

    Grupo grupo;
    User user;
    GrupoCreateDTO grupoDTO;

    @BeforeAll
    void init(){
        controller = new GrupoController(uRepo, repo, lRepo);

        user = new User();
        grupoDTO = new GrupoCreateDTO();

        grupo = new Grupo();
        grupo.setActivo(true);
        grupo.setCounters(new ArrayList<>());
        grupo.setId(1L);
        grupo.setNombre("test");
        grupo.setUser(user);
    }

    *//**
     * saveGroup()
     *//*
    @Test
    @Order(1)
    void saveGroupTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(LocalDateTime.now().isAfter(any())).thenReturn(false);
        when(mapper.grupoFromDTO(any())).thenReturn(grupo);
        when(uRepo.existsById(any())).thenReturn(true);
        when(uRepo.findById(any())).thenReturn(Optional.of(user));
        when(repo.save(any())).thenReturn(grupo);

        ResponseEntity<?> response = controller.saveGroup(grupoDTO, "");
        String ans = (String)response.getBody();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans, "Grupo creado")
        );
    }*/
}
