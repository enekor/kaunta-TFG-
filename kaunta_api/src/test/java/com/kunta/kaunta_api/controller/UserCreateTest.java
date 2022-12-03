package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.dto.UserRegiterDTO;
import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.model.User;
import com.kunta.kaunta_api.repository.UserRepository;
import com.kunta.kaunta_api.utils.IsAfterCheck;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.kunta.kaunta_api.repository.LoginRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ContextConfiguration(classes = {UserController.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserCreateTest {
    
    @InjectMocks
    private UserController controller;

    @MockBean
    private LoginRepository lRepo;

    @MockBean
    private UserRepository repo;

    @MockBean
    private IsAfterCheck isAfterCheck;

    private Login loginToken;
    private User user;
    private UserRegiterDTO reg;
    private Login login;

    private Login loginValido;
    private Login loginInvalido;

    @BeforeAll
    public void init(){
        user = new User();
        user.setGrupos(new ArrayList<>());
        user.setId(1L);
        user.setUsername("nombre");
        user.setPassword("1234");

        reg = new UserRegiterDTO();
        reg.setUser("nombre");
        reg.setPassword("1234");

        login = new Login();
        login.setToken("");
        login.setIdUsuario(1L);

        loginToken = new Login();
        loginToken.setToken("token");

        loginValido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(2030,12,12,14,45));

        loginInvalido = new Login();
        loginInvalido.setIdUsuario(1L);
        loginInvalido.setToken("");
        loginInvalido.setExpireDate(LocalDateTime.of(2019,12,12,14,45));

        isAfterCheck = new IsAfterCheck();

        controller = new UserController(repo,lRepo,isAfterCheck);
    }

    /**
     * register()
     */
    @Test
    void registerSuccessTest(){
        when(repo.existsByUsername(any())).thenReturn(false);
        when(lRepo.save(any())).thenReturn(loginToken);

        ResponseEntity<?> response = controller.register(reg);
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("token",ans),
                ()->assertEquals(202,response.getStatusCodeValue())
        );
    }

    @Test
    void registerFailTest(){
        when(repo.existsByUsername(any())).thenReturn(true);

        ResponseEntity<?> response = controller.register(reg);
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("ya existe usuario con ese nombre",ans),
                ()->assertEquals(409,response.getStatusCodeValue())
        );
    }

    /**
     * login()
     */
    @Test
    void loginSuccessNewTest(){
        user.setPassword("1234");

        when(repo.existsByUsername(any())).thenReturn(true);
        when(repo.findByUsername(any())).thenReturn(user);
        when(lRepo.existsByIdUsuario(user.getId())).thenReturn(false);
        when(lRepo.save(any())).thenReturn(loginToken);

        ResponseEntity<?> response = controller.login("","1234");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("token",ans),
                ()->assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void loginSuccessExistsSuccessTest(){
        user.setPassword("1234");

        when(repo.existsByUsername(any())).thenReturn(true);
        when(repo.findByUsername(any())).thenReturn(user);
        when(lRepo.existsByIdUsuario(user.getId())).thenReturn(true);
        when(lRepo.findByIdUsuario(user.getId())).thenReturn(loginValido);

        ResponseEntity<?> response = controller.login("","1234");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("",ans),
                ()->assertEquals(200,response.getStatusCodeValue())
        );
    }

    @Test
    void loginSuccessExistsFailTest(){
        user.setPassword("1234");

        when(repo.existsByUsername(any())).thenReturn(true);
        when(repo.findByUsername(any())).thenReturn(user);
        when(lRepo.existsByIdUsuario(user.getId())).thenReturn(true);
        when(lRepo.findByIdUsuario(user.getId())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.login("","1234");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );
    }

    /**
     * me()
     */
    @Test
    void meSuccessTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginValido);
        when(repo.findById(any())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = controller.me("");
        User ans = (User) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals(user.getUsername(),ans.getUsername()),
                ()->assertEquals(200,response.getStatusCodeValue())
        );

    }

    @Test
    void meFailTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(lRepo.findByToken(any())).thenReturn(loginInvalido);

        ResponseEntity<?> response = controller.me("");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("La sesion ha expirado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );

    }

    @Test
    void meSessionFailTest(){
        when(lRepo.existsByToken(any())).thenReturn(false);

        ResponseEntity<?> response = controller.me("");
        String ans = (String) response.getBody();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("No esta autorizado",ans),
                ()->assertEquals(401,response.getStatusCodeValue())
        );

    }


}
