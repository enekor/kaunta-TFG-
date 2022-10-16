package com.kunta.kaunta_api.controller;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunta.kaunta_api.dto.UserRegiterDTO;
import com.kunta.kaunta_api.model.User;
import com.kunta.kaunta_api.reporitory.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository repo;


    @GetMapping("/login")
    public ResponseEntity<?> login (@RequestParam String username, @RequestParam String password){
        
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(repo.existsByUsername(username)){

            User u = repo.findByUsername(username);
            if(u.getPassword().equals(password)){
                String token = UUID.randomUUID().toString();
                u.setToken(token);

                repo.save(u);

                status = HttpStatus.OK;
                ans = token;

            }else{
                status = HttpStatus.UNAUTHORIZED;
                ans = "Error en el usuario o contraseña";
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error en el usuario o contraseña";
        }

        return ResponseEntity.status(status).body(ans);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegiterDTO userReg){

        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(repo.existsByUsername(userReg.getUser())){
            status = HttpStatus.NOT_FOUND;
            ans = "ya existe usuario con ese nombre";
        }else{
            status = HttpStatus.ACCEPTED;
            
            User u = new User();
            u.setUsername(userReg.getUser());
            u.setPassword(userReg.getPassword());
            u.setGrupos(new ArrayList<>());
            
            User ret = repo.save(u);

            ans = "Usuario creado con exito";
        }
        return ResponseEntity.status(status).body(ans);
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> me(@RequestParam String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = "";
        
        Optional<User> optionaUser = repo.findByToken(token);
        if(optionaUser.isPresent()){
            status = HttpStatus.OK;
            ans = optionaUser.get();
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "No esta autorizado";
        }

        return ResponseEntity.status(status).body(ans);
    }

}
