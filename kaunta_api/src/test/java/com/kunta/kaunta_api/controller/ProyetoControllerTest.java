package com.kunta.kaunta_api.controller;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {ProyectoController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProyetoControllerTest {

    @InjectMocks
    private ProyectoController controller;

    @MockBean
    private ProyectoRepository repo;

    private List<Proyecto> proyectos;

    private Proyecto proyecto;

    @BeforeAll
    void init(){
        controller = new ProyectoController(repo);
        proyecto = new Proyecto();
        proyecto.setNombre("pepe");
        proyecto.setId(1L);
        proyectos = List.of(proyecto);
    }

    @Test
    @Order(1)
    void insertProyectoTest(){
        when(repo.save(any())).thenReturn(proyecto);

        ResponseEntity<?> response = controller.insertProyecto(proyecto);

        Proyecto p = (Proyecto) response.getBody();

        assertAll(
                ()-> assertNotNull(p),
                ()-> assertEquals(proyecto.getNombre(),p.getNombre())
        );
    }

    @Test
    @Order(2)
    void getProyectoByIdTest(){
        when(repo.findById(any())).thenReturn(Optional.of(proyecto));
        when(repo.existsById(any())).thenReturn(true);

        ResponseEntity<?> response = controller.getProyectoById(1L);

        Optional<Proyecto> p = Optional.of((Proyecto) response.getBody());

        assertAll(
                ()->assertNotNull(p),
                ()->assertTrue(p.isPresent()),
                ()->assertEquals(proyecto.getId(),p.get().getId()),
                ()->assertEquals(proyecto.getNombre(),p.get().getNombre())
        );
    }

    @Test
    @Order(3)
    void deleteProyectoTest(){
        doNothing().when(repo).deleteById(any());
        when(repo.findById(any())).thenReturn(Optional.of(proyecto));
        when(repo.existsById(any())).thenReturn(true);

        ResponseEntity<?> response = controller.deleteProyecto(proyecto.getId());


        assertAll(
                ()->assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode())
        );
        verify(repo,times(2)).existsById(any());
        verify(repo,times(1)).findById(any());
    }

    @Test
    @Order(4)
    void getByIdNotFound(){
        when(repo.existsById(any())).thenReturn(false);

        assertAll(
                ()->assertThrows(EntityNoFoundException.class,()->controller.getProyectoById(1L))
        );
    }
}