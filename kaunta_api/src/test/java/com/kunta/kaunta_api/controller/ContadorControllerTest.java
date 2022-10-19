package com.kunta.kaunta_api.controller;

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

@SpringBootTest
@ContextConfiguration(classes = {ContadorController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContadorControllerTest {

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

    private Contador contador;

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
    }

    @Test
    @Order(1)
    void nuevoContadorTest(){
        when(repo.save(any())).thenReturn(contador);
        when(lRepo.existsByToken(any())).thenReturn(true);

        ResponseEntity<?> response = controller.createCounter()
    }
}
