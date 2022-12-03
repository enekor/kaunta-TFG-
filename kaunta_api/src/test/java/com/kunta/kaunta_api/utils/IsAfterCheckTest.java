package com.kunta.kaunta_api.utils;

import com.kunta.kaunta_api.model.Login;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {IsAfterCheck.class})
@SpringBootTest
public class IsAfterCheckTest {

    private Login loginValido;
    private Login loginInvalido;
    private IsAfterCheck check;

    @BeforeAll
    void init(){
        loginValido = new Login();
        loginValido.setIdUsuario(1L);
        loginValido.setToken("");
        loginValido.setExpireDate(LocalDateTime.of(2030,12,12,14,45));

        loginInvalido = new Login();
        loginInvalido.setIdUsuario(1L);
        loginInvalido.setToken("");
        loginInvalido.setExpireDate(LocalDateTime.of(2019,12,12,14,45));

        check = new IsAfterCheck();
    }

    @Test
    void isAfterTest(){

        boolean ans = check.isAfter(loginInvalido);

        assertAll(
                ()->assertNotNull(ans),
                ()->assertTrue(ans)
        );
    }

    @Test
    void isNotAfterTest(){

        boolean ans = check.isAfter(loginValido);

        assertAll(
                ()->assertNotNull(ans),
                ()->assertFalse(ans)
        );
    }
}
