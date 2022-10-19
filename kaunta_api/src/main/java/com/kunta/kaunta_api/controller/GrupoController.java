package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.reporitory.LoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kunta.kaunta_api.dto.GrupoCreateDTO;
import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.model.User;
import com.kunta.kaunta_api.reporitory.GrupoRepository;
import com.kunta.kaunta_api.reporitory.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class GrupoController {
    
    private final UserRepository uRepo;
    private final GrupoRepository repo;
    private LoginRepository lRepo;

    @GetMapping("/group/all/{idUser}")
    public ResponseEntity<?> getAllByUSerId(@PathVariable(name = "idUser")long id){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = null;

        if(uRepo.existsById(id)){
            User u = uRepo.findById(id).get();
            ans = u.getGrupos();
            status = HttpStatus.OK;
        }else{
            ans = "No existe usuario con esa id";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(ans);
    }

    @PostMapping("/group/save")
    public ResponseEntity<?> saveGroup(@RequestBody GrupoCreateDTO grupo, @RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)) {

            Login login = lRepo.findByToken(token);
            if (LocalDateTime.now().isAfter(login.getExpireDate())) {
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";

                lRepo.deleteById(login.getId());
            } else {
                if (grupo.getId() == -1) {
                    Grupo add = new Grupo();
                    add.setNombre(grupo.getNombre());
                    add.setActivo(true);
                    if (uRepo.existsById(grupo.getUser())) {
                        add.setUser(uRepo.findById(grupo.getUser()).get());

                        repo.save(add);
                        status = HttpStatus.OK;
                        ans = "Grupo creado";
                    } else {
                        ans = "No existe usuario con id " + grupo.getUser();
                        status = HttpStatus.NOT_FOUND;
                    }
                } else {
                    Grupo update = repo.findById(grupo.getId()).get();
                    update.setNombre(grupo.getNombre());
                    update.setUser(uRepo.findById(grupo.getUser()).get());
                    repo.save(update);

                    ans = "Grupo actualizado con exito";
                    status = HttpStatus.OK;
                }
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error de sesion";
        }

        return ResponseEntity.status(status).body(ans);
    }
    @DeleteMapping("group/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable(name = "id")long id, @RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);
            if(LocalDateTime.now().isAfter(login.getExpireDate())){
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha caducado";

                lRepo.deleteById(login.getId());
            }else{
                if(repo.existsById(id)){
                    Grupo g = repo.findById(id).get();
                    g.setActivo(false);
                    repo.save(g);
                    status = HttpStatus.OK;
                    ans = "Borrado con exito";
                }else{
                    status = HttpStatus.NOT_FOUND;
                    ans = "No existe grupo con id "+id;
                }
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error de sesion";
        }
        return ResponseEntity.status(status).body(ans);
    }

    @PostMapping("/group/restore/{id}")
    public ResponseEntity<?> restoreGroup(@PathVariable(name = "id")long id,@RequestParam("token")String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){

            Login login = lRepo.findByToken(token);
            if(LocalDateTime.now().isAfter(login.getExpireDate())){
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";

                lRepo.deleteById(login.getId());
            }else{
                if(repo.existsById(id)){
                    Grupo g = repo.findById(id).get();
                    g.setActivo(true);
                    repo.save(g);
                    status = HttpStatus.OK;
                    ans = "Grupo restaurado con exito";

                }else{
                    status = HttpStatus.NOT_FOUND;
                    ans = "No existe grupo con id "+id;
                }
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error de sesion";
        }

        return ResponseEntity.status(status).body(ans);
    }
}
