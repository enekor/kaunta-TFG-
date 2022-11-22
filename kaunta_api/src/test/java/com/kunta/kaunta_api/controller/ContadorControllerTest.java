package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.dto.ContadorCreateDTO;
import com.kunta.kaunta_api.dto.EditContadorDTO;
import com.kunta.kaunta_api.mapper.ContadorMapper;
import com.kunta.kaunta_api.model.Contador;
import com.kunta.kaunta_api.reporitory.ContadorRepository;
import com.kunta.kaunta_api.reporitory.GrupoRepository;
import com.kunta.kaunta_api.reporitory.LoginRepository;
import com.kunta.kaunta_api.upload.StorageService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {ContadorController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContadorControllerTest {
/*
    @InjectMocks
    private ContadorController controller;

    @MockBean
    private ContadorRepository repo;

    @MockBean
    private LoginRepository lRepo;

    @MockBean
    private GrupoRepository gRepo;

    @MockBean
    private StorageService sService;

    @MockBean
    private ContadorMapper mapper;

    private Contador contador;
    private ContadorCreateDTO contadorDTO;
    private EditContadorDTO editContadorDTO;

    @BeforeAll
    void init(){
        controller = new ContadorController(repo,lRepo,gRepo,sService);
        contador = new Contador();
        contador.setId(1L);
        contador.setName("Test");
        contador.setActive(true);
        contador.setGroup(null);
        contador.setCount(0);
        contador.setImage("imagen.png");
        contador.setDescrition("testDesc");

        contadorDTO = new ContadorCreateDTO();

        editContadorDTO = new EditContadorDTO();
        editContadorDTO.setName("test");
        editContadorDTO.setDescripcion("test");
        editContadorDTO.setCounter(1);

    }

    *//**
     * createCounter()
     *//*
    @Test
    @Order(1)
    void nuevoContadorTest(){
        when(repo.save(any())).thenReturn(contador);
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(mapper.contadorFromDTO(any())).thenReturn(contador);
        when(LocalDateTime.now().isAfter(any())).thenReturn(false);

        ResponseEntity<?> response = controller.createCounter(contadorDTO,null,"");

        String ans = (String) response.getBody();

        assertAll(
                ()-> assertNotNull(ans),
                ()-> assertEquals(ans,"Contador creado con exito")
        );
    }

    *//**
     * updateCounter()
     *//*
    @Test
    @Order(2)
    void updateCotadorTest(){
        when(LocalDateTime.now().isAfter(any())).thenReturn(false);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.save(any())).thenReturn(contador);
        when(repo.findById(any())).thenReturn(Optional.of(contador));
        when(lRepo.existsByToken(any())).thenReturn(true);

        ResponseEntity<?> response = controller.updateCounter(editContadorDTO,"");
        String ans = (String)response.getBody();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans,"Contador actualizado con exito" )
        );
    }

    *//**
     * deleteContador()
     *//*
    @Test
    @Order(3)
    void deleteContadorTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(LocalDateTime.now().isAfter(any())).thenReturn(false);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.save(any())).thenReturn(contador);

        ResponseEntity<?> response = controller.deleteContador(1, "");
        String ans = (String) response.getBody();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans, "Contador borrado con exito")
        );
    }

    *//**
     * restoreContador()
     *//*
    @Test
    @Order(4)
    void restoreContadorTest(){

        when(lRepo.existsByToken(any())).thenReturn(true);
        when(LocalDateTime.now().isAfter(any())).thenReturn(false);
        when(repo.existsById(any())).thenReturn(true);
        when(repo.findById(any())).thenReturn(Optional.of(contador));
        when(repo.save(any())).thenReturn(contador);

        ResponseEntity<?> response = controller.restoreContador(1, "");
        String ans = (String)response.getBody();

        assertAll(
            ()->assertNotNull(ans),
            ()->assertEquals(ans,"Contador restaurado con exito")
        );
    }*/
}
