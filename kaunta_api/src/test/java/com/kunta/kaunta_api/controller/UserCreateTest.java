package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.dto.UserDTO;
import com.kunta.kaunta_api.dto.UserRegiterDTO;
import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.model.User;
import com.kunta.kaunta_api.reporitory.UserRepository;
import com.kunta.kaunta_api.utils.IsAfterCheck;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.kunta.kaunta_api.reporitory.LoginRepository;

import java.time.LocalDate;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserCreateTest {
    
    @InjectMocks
    private UserController uController;

    @MockBean
    private LoginRepository lRepo;

    @MockBean
    private UserRepository uRepo;

    @MockBean
    private IsAfterCheck isAfterCheck;

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

        loginValido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(2030,12,12,14,45));

        loginInvalido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(1800,12,12,14,45));

        uController = new UserController(uRepo,lRepo,isAfterCheck);
    }

    /**
     * register
     */
    @Test
    @Order(1)
    void registerSuccessTest(){
        when(uRepo.existsByUsername(any())).thenReturn(false);

        ResponseEntity<?> response = uController.register(reg);
        int ans = response.getStatusCodeValue();

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals(200,ans)
        );
    }

    @Test
    @Order(2)
    void loginSuccessNewTest(){
        when(uRepo.existsByUsername(any())).thenReturn(true);
        when(uRepo.findByUsername(any())).thenReturn(user);
        when(LocalDate.now().isAfter(any())).thenReturn(false);
        when(lRepo.existsByIdUsuario(any())).thenReturn(false);

        ResponseEntity<?> response = uController.login(user.getUsername(),user.getPassword());
        String ans = (String)response.getBody();

        assertAll(
                ()->assertEquals(200,response.getStatusCodeValue()),
                ()->assertNotNull(ans)
        );
    }

    @Test
    @Order(4)
    void loginSuccessExistsTest(){
        when(uRepo.existsByUsername(any())).thenReturn(true);
        when(uRepo.findByUsername(any())).thenReturn(user);
        when(LocalDate.now().isAfter(any())).thenReturn(false);
        when(lRepo.existsByIdUsuario(any())).thenReturn(true);
        when(lRepo.findByIdUsuario(any())).thenReturn(login);

        ResponseEntity<?> response = uController.me("");
        String ans = (String)response.getBody();

        assertAll(
                ()->assertEquals(200,response.getStatusCodeValue()),
                ()->assertNotNull(ans)
        );

    }

    @Test
    @Order(3)
    void meSuccessTest(){
        when(lRepo.existsByToken(any())).thenReturn(true);
        when(LocalDate.now().isAfter(any())).thenReturn(false);
        when(uRepo.findById(any())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = uController.me("");
        UserDTO ans = (UserDTO)response.getBody();

        assertAll(
                ()->assertEquals(response.getStatusCodeValue(),200),
                ()-> {
                    assert ans != null;
                    assertEquals(ans.getUsername(),user.getUsername());
                },
                ()-> {
                    assert ans != null;
                    assertEquals(ans.getGrupos(),user.getGrupos());
                }
        );
    }

}
