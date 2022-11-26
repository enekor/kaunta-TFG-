package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.utils.IsAfterCheck;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import com.kunta.kaunta_api.reporitory.*;
import com.kunta.kaunta_api.mapper.*;
import com.kunta.kaunta_api.model.*;
import com.kunta.kaunta_api.dto.*;

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
    
    @InjectMocks
    private GrupoController controller;

    @MockBean
    private LoginRepository lRepo;

    @MockBean
    private GrupoRepository repo;

    @MockBean
    private UserRepository uRepo;

    @MockBean
    private IsAfterCheck isAfterCheck;

    @MockBean
    private GrupoMapper mapper;

    private Grupo grupo;
    private User user;
    private GrupoCreateDTO grupoDTO;
    private Login loginInvalido;
    private Login loginValido;

    @BeforeAll
    void init(){
        user = new User();
        grupoDTO = new GrupoCreateDTO();

        grupo = new Grupo();
        grupo.setActivo(true);
        grupo.setCounters(new ArrayList<>());
        grupo.setId(1L);
        grupo.setNombre("test");
        grupo.setUser(user);

        loginValido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(2030,12,12,14,45));

        loginInvalido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(1800,12,12,14,45));

        controller = new GrupoController(uRepo, repo, lRepo,isAfterCheck);
    }

    /**
     * saveGroup()
     */
    @Test
    @Order(1)
    void saveGroupTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
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
    }

    /**
     * updateGroup()
     */
    @Test
    @Order(2)
    void updateGroupTest(){
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.save(any())).thenReturn(grupo);
        when(repo.findById(any())).thenReturn(Optional.of(grupo));
        when(lRepo.existsByToken(any())).thenReturn(true);

        ResponseEntity<?> response = controller.editGroup("aa","token",grupo.getId());
        int ans = response.getStatusCodeValue();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans,200)
        );
    }

    /**
     * deleteContador()
     */
    @Test
    @Order(3)
    void deleteContadorTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.save(any())).thenReturn(grupo);

        ResponseEntity<?> response = controller.deleteGroup(1, "");
        int ans = response.getStatusCodeValue();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans, 200)
        );
    }

    /**
     * restoreContador()
     */
    @Test
    @Order(4)
    void restoreContadorTest(){

        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.findById(any())).thenReturn(Optional.of(grupo));
        when(repo.save(any())).thenReturn(grupo);

        ResponseEntity<?> response = controller.restoreGroup(1, "");
        int ans = response.getStatusCodeValue();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans,200)
        );
    }
}
