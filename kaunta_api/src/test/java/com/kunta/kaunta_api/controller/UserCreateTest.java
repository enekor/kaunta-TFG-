package com.kunta.kaunta_api.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.kunta.kaunta_api.reporitory.LoginRepository;

@SpringBootTest
@ContextConfiguration(classes = {UserController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserCreateTest {
    
    @InjectMocks
    private UserController uController;

    @MockBean
    private LoginRepository lRepo;

    private User user;
    private List<Grupo> grupos;
    private UserRegiterDTO reg;
    
}
