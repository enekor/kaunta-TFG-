package com.kunta.kaunta_api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
import com.kunta.kaunta_api.utils.Token;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository repo;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam(name = "user")String user, @RequestParam(name = "password")String password){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = null;
        
        System.out.println(repo.existsByName(user));
        if(repo.existsByName(user)){
            status = HttpStatus.ACCEPTED;
            User u = repo.findByName(user);

            LocalDateTime dia = LocalDateTime.now().plusDays(7);
            //ans = Token.getInstance().tokenGenerator(user, dia.toString());
            ans = u;
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Usuario o password incorrectos";
        }
        return ResponseEntity.status(status).body(ans);
    }

    @GetMapping("/user/token")
    public ResponseEntity<?> getUserByToken(@RequestParam(name = "auth")String token){

        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = null;

        if(Token.getInstance().tokenVerification(token)){
            status = HttpStatus.OK;
            User u = repo.findByName(Token.userName);
            ans = u;
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "El token ha expirado";
        }

        return ResponseEntity.status(status).body(ans); 
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegiterDTO userReg){

        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = null;

        if(repo.existsByName(userReg.getUser())){
            status = HttpStatus.NOT_FOUND;
            ans = "ya existe usuario con ese nombre";
        }else{
            status = HttpStatus.ACCEPTED;
            
            User u = new User();
            u.setName(userReg.getUser());
            u.setPassword(userReg.getPassword());
            u.setGrupos(new ArrayList<>());
            
            User ret = repo.save(u);

            LocalDateTime dia = LocalDateTime.now().plusDays(7);
            //String ret = Token.getInstance().tokenGenerator(userReg.getUser(), dia.toString());

            ans = ret;
        }
        return ResponseEntity.status(status).body(ans);
    }

}
