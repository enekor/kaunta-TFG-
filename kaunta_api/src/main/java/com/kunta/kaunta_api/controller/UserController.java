package com.kunta.kaunta_api.controller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.reporitory.LoginRepository;
import com.kunta.kaunta_api.utils.IsAfterCheck;
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
    private final IsAfterCheck isAfterCheck;

    /**
     * en base a un usuario y una contraseña, verifica que ambas sean pertenecientes a
     * un usuario de la base de datos, genera un token, y devuelve dicho token
     * @param username nombre del usuario
     * @param password password del usuario
     * @return token de sesion o mensaje de error
     */
    @GetMapping("/login")
    public ResponseEntity<?> login (@RequestParam("username") String username, @RequestParam("password") String password){
        
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(repo.existsByUsername(username)){

            User u = repo.findByUsername(username);
            if(u.getPassword().equals(password)){
                String token = "";

                if(lRepo.existsByIdUsuario(u.getId())){
                    Login login = lRepo.findByIdUsuario(u.getId());

                    if(!isAfterCheck.isAfter(login)){
                        status = HttpStatus.UNAUTHORIZED;
                        ans = "La sesion ha expirado";

                        lRepo.deleteById(login.getId());
                    }else{
                        token = login.getToken();

                        status = HttpStatus.OK;
                        ans = token;
                    }
                }else{
                    Login login = new Login();
                    login.setToken(UUID.randomUUID().toString());
                    login.setIdUsuario(u.getId());
                    login.setExpireDate(LocalDateTime.now().plusWeeks(2));

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

    /**
     * crea un usuario a partir del objeto pasado por dto, siempre y
     * cuando el nombre de usuario no exista previamente en la base de datos
     * @param userReg info del usuario a crear
     * @return confirmacion de modificacion o error
     */
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

    /**
     * devuelve el usuario al que pertenece el token
     * @param token la sesion
     * @return json del usuario o mensaje de error
     */
    @GetMapping("/user/me")
    public ResponseEntity<?> me(@RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = "";
        

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);

            if(!isAfterCheck.isAfter(login)){
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";

                lRepo.deleteById(login.getId());
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
