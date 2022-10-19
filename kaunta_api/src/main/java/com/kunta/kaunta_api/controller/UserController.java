package com.kunta.kaunta_api.controller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.reporitory.LoginRepository;
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
    private final LoginRepository lRepo;


    @GetMapping("/login")
    public ResponseEntity<?> login (@RequestParam String username, @RequestParam String password){
        
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(repo.existsByUsername(username)){

            User u = repo.findByUsername(username);
            if(u.getPassword().equals(password)){
                String token = "";
                LocalDateTime now = LocalDateTime.now();

                if(lRepo.existsByIdUsuario(u.getId())){
                    Login login = lRepo.findByIdUsuario(u.getId());

                    if(now.isAfter(login.getExpireDate())){
                        status = HttpStatus.UNAUTHORIZED;
                        ans = "La sesion ha expirado";
                    }else{
                        token = login.getToken();

                        status = HttpStatus.OK;
                        ans = token;
                    }
                }else{
                    Login login = new Login();
                    login.setToken(UUID.randomUUID().toString());
                    login.setIdUsuario(u.getId());
                    login.setExpireDate(now.plusWeeks(2));

                    lRepo.save(login);

                    status = HttpStatus.OK;
                    ans = login.getToken();
                }
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
            status = HttpStatus.CONFLICT;
            ans = "ya existe usuario con ese nombre";
        }else{
            LocalDateTime now = LocalDateTime.now();
            
            User u = new User();
            u.setUsername(userReg.getUser());
            u.setPassword(userReg.getPassword());
            u.setGrupos(new ArrayList<>());
            
            repo.save(u);

            Login login = new Login();
            login.setToken(UUID.randomUUID().toString());
            login.setIdUsuario(u.getId());
            login.setExpireDate(now.plusWeeks(2));

            lRepo.save(login);

            status = HttpStatus.ACCEPTED;
            ans = login.getToken();
        }
        return ResponseEntity.status(status).body(ans);
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> me(@RequestParam String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = "";
        LocalDateTime now = LocalDateTime.now();
        

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);

            if(now.isAfter(login.getExpireDate())){
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";
            }else{
                status = HttpStatus.OK;
                ans =repo.findById(login.getIdUsuario());
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "No esta autorizado";
        }

        return ResponseEntity.status(status).body(ans);
    }

}
