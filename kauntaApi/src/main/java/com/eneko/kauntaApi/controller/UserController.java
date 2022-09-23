package com.eneko.kauntaApi.controller;

import com.eneko.kauntaApi.model.User;
import com.eneko.kauntaApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repo;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") long id){
        if(repo.existsById(id)){
            User u = repo.findById(id).get();
            return ResponseEntity.ok(u);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe usuario con id "+id);
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<?> saveContador(@RequestBody User user){
        User u = repo.save(user);
        return ResponseEntity.ok(u);
    }
}