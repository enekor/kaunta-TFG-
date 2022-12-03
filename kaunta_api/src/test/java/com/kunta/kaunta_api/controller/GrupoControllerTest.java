package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.utils.IsAfterCheck;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import com.kunta.kaunta_api.repository.*;
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
    private GrupoMapper grupoMapper;

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

        grupoDTO = new GrupoCreateDTO();
        grupoDTO.setNombre("test");
        grupoDTO.setUser(1L);

        user = new User();
        user.setId(5L);
        user.setPassword("pass");
        user.setUsername("test");
        user.setGrupos(new ArrayList<>());

        loginValido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(2030,12,12,14,45));

        loginInvalido = new Login();
        loginInvalido.setIdUsuario(1L);
        loginInvalido.setToken("");
        loginInvalido.setExpireDate(LocalDateTime.of(2019,12,12,14,45));

        grupoMapper = new GrupoMapper();

        isAfterCheck = new IsAfterCheck();

        controller = new GrupoController(uRepo, repo, lRepo,isAfterCheck,grupoMapper);
    }

    /**
     * saveGroup()
     */
    @Test
    void createGroupSuccessTest(){
        grupoDTO.setId(-1);

        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(uRepo.existsById(any())).thenReturn(true);
        when(uRepo.findById(any())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = controller.saveGroup(grupoDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()-> assertNotNull(ans),
                ()-> assertEquals("Grupo creado",ans),
                ()->assertEquals(200,response.getStatusCodeValue())
        );

    }

    @Test
    void createGroupUserNotFoundTest(){
        grupoDTO.setId(-1);

        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(uRepo.existsById(any())).thenReturn(false);

        ResponseEntity<?> response = controller.saveGroup(grupoDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()-> assertNotNull(ans),
                ()-> assertEquals("No existe usuario con id 1",ans),
                ()->assertEquals(404,response.getStatusCodeValue())
        );

    }


    @Test
    void updateGroupSuccessTest(){
        grupoDTO.setId(1);

        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(uRepo.existsById(any())).thenReturn(true);
        when(repo.findById(any())).thenReturn(Optional.of(grupo));
        when(uRepo.findById(any())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = controller.saveGroup(grupoDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()-> assertNotNull(ans),
                ()-> assertEquals("Grupo actualizado con exito",ans),
                ()->assertEquals(200,response.getStatusCodeValue())
        );

    }

    @Test
    void saveGroupLoginError(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.saveGroup(grupoDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void saveGroupSessionError(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.saveGroup(grupoDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("Error de sesion",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    /**
     * deleteContador()
     */
    @Test
    void deleteContadorSuccessTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.findById(any())).thenReturn(Optional.of(grupo));

        ResponseEntity<?> response = controller.deleteGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("Borrado con exito",ans),
                ()->assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void deleteContadorFailTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(false);

        ResponseEntity<?> response = controller.deleteGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("No existe grupo con id 1",ans),
                ()->assertEquals(404,response.getStatusCodeValue())
        );
    }

    @Test
    void deleteContadorLoginErrorTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.deleteGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha caducado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void deleteContadorSessionErrorTest(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.deleteGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha caducado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    /**
     * restoreContador()
     */
    @Test
    void restoreContadorSuccessTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.findById(any())).thenReturn(Optional.of(grupo));

        ResponseEntity<?> response = controller.restoreGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("Grupo restaurado con exito",ans),
                ()->assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void restoreContadorFailTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(false);

        ResponseEntity<?> response = controller.restoreGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("No existe grupo con id 1",ans),
                ()->assertEquals(404,response.getStatusCodeValue())
        );
    }

    @Test
    void restoreContadorLoginErrorTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.restoreGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void restoreContadorSessionErrorTest(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.restoreGroup(1,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("Error de sesion",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }
}
