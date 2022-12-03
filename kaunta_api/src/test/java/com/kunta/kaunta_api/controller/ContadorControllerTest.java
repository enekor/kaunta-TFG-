package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.dto.ContadorCreateDTO;
import com.kunta.kaunta_api.dto.EditContadorDTO;
import com.kunta.kaunta_api.mapper.ContadorMapper;
import com.kunta.kaunta_api.model.Contador;
import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.repository.ContadorRepository;
import com.kunta.kaunta_api.repository.GrupoRepository;
import com.kunta.kaunta_api.repository.LoginRepository;
import com.kunta.kaunta_api.upload.StorageService;

import com.kunta.kaunta_api.utils.IsAfterCheck;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {ContadorController.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContadorControllerTest {

    @InjectMocks
    ContadorController controller;

    @MockBean
    ContadorRepository repo;

    @MockBean
    LoginRepository lRepo;

    @MockBean
    GrupoRepository gRepo;

    @MockBean
    StorageService sService;

    @MockBean
    ContadorMapper mapper;

    @MockBean
    IsAfterCheck isAfterCheck;

    Contador contador;
    Grupo grupo;
    ContadorCreateDTO contadorDTO;
    EditContadorDTO editContadorDTO;

    Login loginValido;
    Login loginInvalido;

    @BeforeAll
    void init(){
        contador = new Contador();
        contador.setId(1L);
        contador.setName("Test");
        contador.setActive(true);
        contador.setGroup(null);
        contador.setCount(0);
        contador.setImage("imagen.png");
        contador.setDescrition("testDesc");

        contadorDTO = new ContadorCreateDTO();
        contadorDTO.setName("Test");
        contadorDTO.setGroup(5);
        contadorDTO.setCount(0);

        grupo = new Grupo();
        grupo.setActivo(true);
        grupo.setNombre("aa");
        grupo.setCounters(new ArrayList<>());


        editContadorDTO = new EditContadorDTO();
        editContadorDTO.setName("test");
        editContadorDTO.setDescripcion("test");
        editContadorDTO.setCounter(1);
        editContadorDTO.setId(1L);

        loginValido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(2030,12,12,14,45));

        loginInvalido = new Login();
        loginInvalido.setIdUsuario(1L);
        loginInvalido.setToken("");
        loginInvalido.setExpireDate(LocalDateTime.of(2019,12,12,14,45));

        isAfterCheck = new IsAfterCheck();

        mapper = new ContadorMapper();

        controller = new ContadorController(repo,lRepo,gRepo,sService,isAfterCheck,mapper);
    }

    /**
     * createCounter()
     */
    @Test
    void createCounterSuccessTest(){
        when(repo.save(any())).thenReturn(contador);
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(gRepo.existsById(any())).thenReturn(true);
        when(gRepo.findById(any())).thenReturn(Optional.of(grupo));

        ResponseEntity<?> response = controller.createCounter(contadorDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()-> assertNotNull(ans),
                ()-> assertEquals("Contador creado con exito",ans),
                ()-> assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void createCounterSesionError(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.createCounter(contadorDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()-> assertEquals("Error de sesion",ans),
                ()-> assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void createCounterGroupNotFound(){
        when(repo.save(any())).thenReturn(contador);
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(gRepo.existsById(any())).thenReturn(false);

        ResponseEntity<?> response = controller.createCounter(contadorDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()-> assertEquals("No existe grupo con id 5",ans),
                ()->assertEquals(404,response.getStatusCodeValue())
        );
    }

    @Test
    void createCounterTokenFail(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.createCounter(contadorDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    /**
     * updateCounter()
     */
    @Test
    void updateCotadorTest(){
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.save(any())).thenReturn(contador);
        when(repo.findById(any())).thenReturn(Optional.of(contador));
        when(lRepo.existsByToken(any())).thenReturn(true);

        ResponseEntity<?> response = controller.updateCounter(editContadorDTO,"");
        String ans = (String)response.getBody();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans,"Contador actualizado con exito"),
            ()->assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void updateCounterCounterNotFound(){
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(false);
        when(repo.save(any())).thenReturn(contador);
        when(lRepo.existsByToken(any())).thenReturn(true);

        ResponseEntity<?> response = controller.updateCounter(editContadorDTO,"");
        String ans = (String)response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("No existe contador con id 1",ans),
                ()->assertEquals(404,response.getStatusCodeValue())
        );
    }

    @Test
    void updateCounterTokenFail(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.updateCounter(editContadorDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void updateCounterSesionError(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.updateCounter(editContadorDTO,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()-> assertEquals("Error de sesion",ans),
                ()-> assertEquals(401,response.getStatusCodeValue())
        );
    }

    /**
     * deleteContador()
     */
    @Test
    void deleteContadorTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.save(any())).thenReturn(contador);
        when(repo.findById(any())).thenReturn(Optional.of(contador));

        ResponseEntity<?> response = controller.deleteContador(1, "");
        String ans = (String) response.getBody();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans, "Contador borrado con exito"),
            ()->assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void deleteCounterTokenFail(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.deleteContador(1L,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void deleteCounterSesionError(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.deleteContador(1L,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()-> assertEquals("Error de sesion",ans),
                ()-> assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void deleteCounterCounterNotFound(){
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(false);
        when(lRepo.existsByToken(any())).thenReturn(true);

        ResponseEntity<?> response = controller.deleteContador(1,"");
        String ans = (String)response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("No se encontro contador con id 1",ans),
                ()->assertEquals(404,response.getStatusCodeValue())
        );
    }



    /**
     * restoreContador()
     */
    @Test
    void restoreContadorTest(){

        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.findById(any())).thenReturn(Optional.of(contador));
        when(repo.save(any())).thenReturn(contador);
        when(repo.findById(any())).thenReturn(Optional.of(contador));

        ResponseEntity<?> response = controller.restoreContador(1, "");
        String ans = (String)response.getBody();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans,"Contador restaurado con exito"),
            ()->assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void restoreCounterTokenFail(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.deleteContador(1L,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void restoreCounterSesionError(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.deleteContador(1L,"");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()-> assertEquals("Error de sesion",ans),
                ()-> assertEquals(401,response.getStatusCodeValue())
        );
    }

    @Test
    void restoreCounterCounterNotFound(){
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.existsById(any())).thenReturn(false);
        when(lRepo.existsByToken(any())).thenReturn(true);

        ResponseEntity<?> response = controller.deleteContador(1,"");
        String ans = (String)response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("No se encontro contador con id 1",ans),
                ()->assertEquals(404,response.getStatusCodeValue())
        );
    }
}
