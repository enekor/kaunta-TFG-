package com.kunta.kaunta_api.controller;

import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.model.User;
import com.kunta.kaunta_api.reporitory.LoginRepository;
import com.kunta.kaunta_api.utils.IsAfterCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kunta.kaunta_api.dto.GrupoCreateDTO;
import com.kunta.kaunta_api.mapper.GrupoMapper;
import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.reporitory.GrupoRepository;
import com.kunta.kaunta_api.reporitory.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class GrupoController {
    
    private final UserRepository uRepo;
    private final GrupoRepository repo;
    private final LoginRepository lRepo;
    private final IsAfterCheck isAfterCheck;

    /**
     * devuelve los grupos que pertenezcan al usuario del token,
     * filtrados por el estado indicado por parametro
     * @param token la sesion
     * @param active si el grupo esta activo o no
     * @return json de los grupos o error
     */
    @GetMapping("/group/all/{activo}")
    public ResponseEntity<?> getAllByUser(@RequestParam("token")String token,@PathVariable(name = "activo")boolean active){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = null;

        if(lRepo.existsByToken(token)){
            User u = uRepo.findById(lRepo.findByToken(token).getIdUsuario()).get();

            ans = repo.findAllByUserAndActivo(u,active);
            status = HttpStatus.OK; 
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error de sesion";
        }

        return ResponseEntity.status(status).body(ans);
    }

    /**
     * crea un grupo en base a la informacion del dto pasado por parametro y lo
     * añade al usuario al que pertenece el token
     * @param grupo info del grupo a crear
     * @param token la sesion
     * @return confirmacion de modificacion o error
     */
    @PostMapping("/group/save")
    public ResponseEntity<?> saveGroup(@RequestBody GrupoCreateDTO grupo, @RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)) {

            Login login = lRepo.findByToken(token);
            if (!isAfterCheck.isAfter(login)) {
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";

                lRepo.deleteById(login.getId());
            } else {
                if (grupo.getId() == -1) {
                    Grupo add = GrupoMapper.getInstance().grupoFromDTO(grupo);
                    
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

    /**
     * cambia el nombre del grupo por el pasado por parametro
     * @param name nombre a cambiar del grupo
     * @param token la sesion
     * @param id id del grupo
     * @return confirmacion de modificacion o error
     */
    @PutMapping("group/edit/{id}")
    public ResponseEntity<?> editGroup(@RequestParam("name")String name,@RequestParam("token")String token,@PathVariable(name = "id")long id){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){

            Login login = lRepo.findByToken(token);
            if(!isAfterCheck.isAfter(login)){
                ans = "La sesion ha expirado";
                status = HttpStatus.UNAUTHORIZED;
            }else{
                if(repo.existsById(id)){
                    Grupo g = repo.findById(id).get();

                    g.setNombre(name);

                    repo.save(g);

                    ans = "Grupo cambiado con exito";
                    status = HttpStatus.OK;
                }else{
                    ans = "No se ha encontrado grupo con id "+id;
                    status = HttpStatus.NOT_FOUND;
                }
            }
        }else{
            ans = "Error de sesion";
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(status).body(ans);
    }

    /**
     * pone como no visible el grupo
     * @param id id del grupo
     * @param token la sesion
     * @return confirmacion de modificacion o error
     */
    @DeleteMapping("group/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable(name = "id")long id, @RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);
            if(!isAfterCheck.isAfter(login)){
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

    /**
     *pone como visible el grupo
     * @param id id del grupo
     * @param token la sesion
     * @return confirmacion de modificacion o error
     */
    @PostMapping("/group/restore/{id}")
    public ResponseEntity<?> restoreGroup(@PathVariable(name = "id")long id,@RequestParam("token")String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){

            Login login = lRepo.findByToken(token);
            if(!isAfterCheck.isAfter(login)){
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
