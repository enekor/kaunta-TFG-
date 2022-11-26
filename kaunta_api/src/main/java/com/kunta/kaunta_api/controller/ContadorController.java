package com.kunta.kaunta_api.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.kunta.kaunta_api.dto.CountersOutDto;
import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.reporitory.LoginRepository;
import com.kunta.kaunta_api.utils.IsAfterCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.kunta.kaunta_api.dto.ContadorCreateDTO;
import com.kunta.kaunta_api.dto.EditContadorDTO;
import com.kunta.kaunta_api.mapper.ContadorMapper;
import com.kunta.kaunta_api.model.Contador;
import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.reporitory.ContadorRepository;
import com.kunta.kaunta_api.reporitory.GrupoRepository;
import com.kunta.kaunta_api.upload.StorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ContadorController {
    
    private final ContadorRepository repo;
    private final LoginRepository lRepo;
    private final GrupoRepository gRepo;
    private final StorageService storageService;
    private final IsAfterCheck isAfterCheck;
    private final ContadorMapper contadorMapper;

    /**
     * devuelve los contadores que pertenezcan al grupo especificado, filtrados por el estado indicado por parametro
     * @param active si los contadores son visibles o no
     * @param token la sesion
     * @param group el grupo al que pertenecen los contadores
     * @return json de los contadores o el error
     */
    @GetMapping("/counter/all/{active}")
    public ResponseEntity<?> getAllCountersFromGroupAndActive(@PathVariable(name = "active") boolean active,@RequestParam("token")String token, @RequestParam("group")long group){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = null;

        if(lRepo.existsByToken(token)){

            Login login = lRepo.findByToken(token);
            if(!isAfterCheck.isAfter(login)){
                ans = "La sesion ha caducado";
                status = HttpStatus.UNAUTHORIZED;
            }else{
                if(gRepo.existsById(group)){
                    Grupo g = gRepo.findById(group).get();
                    List<Contador> contadores = repo.findAllByGroupAndActive(g, active);

                    status = HttpStatus.OK;
                    ans = new CountersOutDto(contadores);
                }else{
                    status = HttpStatus.NOT_FOUND;
                    ans = "No se ha encontrado grupo con id "+group;
                }
            }
        }else{
            ans = "Error de sesion";
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(status).body(ans);
    }

    /**
     * crea un contador a partir de los datos proporcionados en el dto pasado por parametro
     * @param contador informacion de creacion de contador
     * @param token la sesion
     * @return String confirmando la creacion o el error
     */
    @PostMapping("/counter/add")
    public ResponseEntity<?> createCounter(@RequestBody ContadorCreateDTO contador, @RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);

            if(!isAfterCheck.isAfter(login)){
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";

                lRepo.deleteById(login.getId());
            }else{
                Contador c = contadorMapper.contadorFromDTO(contador);
                c.setImage(contador.getImage());
                
                if(gRepo.existsById(contador.getGroup())){
                    Grupo g = gRepo.findById(contador.getGroup()).get();
                    c.setGroup(g);

                    repo.save(c);

                    status = HttpStatus.OK;
                    ans = "Contador creado con exito";
                }else{
                    status = HttpStatus.NOT_FOUND;
                    ans = "No existe grupo con id "+contador.getGroup();
                }
            }
        }else{
            ans = "Error de sesion";
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(status).body(ans);
    }

    /**
     * sube la imagen pasada por parametro y devuelve el link de la imagen en la api
     * @param image imagen a subir
     * @param token la sesion
     * @return la url de la imagen
     */
    @PostMapping( "/counter/addImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, @RequestParam("token") String token){
        String ans = "";
        HttpStatus status = HttpStatus.ACCEPTED;

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);
            if(!isAfterCheck.isAfter(login)){
                ans = "La sesion ha expirado";
                status = HttpStatus.UNAUTHORIZED;
            }else{
                String urlImage = null;

                if(!image.isEmpty() && image != null){
                    String imagen = storageService.store(image);
                    urlImage = MvcUriComponentsBuilder.fromMethodName(FicherosController.class,"serveFile",imagen,null).build().toUriString();
                }else{
                    urlImage = "https://www.familiasnumerosascv.org/wp-content/uploads/2015/05/icono-camara.png";
                }

                status = HttpStatus.OK;
                ans = urlImage;
            }
        }else{
            ans = "Error de sesion";
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(status).body(ans);
    }

    /**
     * actualiza el contador sumando el numero pasado por parametro
     * @param c info de contador a modificar
     * @param token la sesion
     * @return confirmacion de modificacion o error
     */
    @PutMapping("/counter/edit")
    public ResponseEntity<?> updateCounter(@RequestBody EditContadorDTO c, @RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);
            if(!isAfterCheck.isAfter(login)){
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";

                lRepo.deleteById(login.getId());
            }else{
                if(repo.existsById(c.getId())){
                    Contador counter = repo.findById(c.getId()).get();
                    counter.setName(c.getName());
                    counter.setDescrition(c.getDescripcion());
                    counter.setCount(counter.getCount()+c.getCounter());

                    repo.save(counter);

                    status = HttpStatus.OK;
                    ans = "Contador actualizado con exito";
                }else{
                    status = HttpStatus.NOT_FOUND;
                    ans = "No existe contador con id "+c.getId();
                }
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error de sesion";
        }

        return ResponseEntity.status(status).body(ans);
    }

    /**
     * pone como no visible el contador
     * @param id id del contador
     * @param token la sesion
     * @return confirmacion de modificacion o error
     */
    @DeleteMapping("/counter/delete/{id}")
    public ResponseEntity<?> deleteContador(@PathVariable(name = "id")long id, @RequestParam("token") String token){
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
                    Contador c = repo.findById(id).get();

                    c.setActive(false);
                    repo.save(c);

                    status = HttpStatus.OK;
                    ans = "Contador borrado con exito";
                }else{
                    status = HttpStatus.NOT_FOUND;
                    ans = "No se encontro contador con id "+id;
                }
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error de sesion";
        }

        return ResponseEntity.status(status).body(ans);
    }

    /**
     *  pone en visible el contador
     * @param id id del contador
     * @param token la sesion
     * @return confirmacion de modificacion o error
     */
    @PostMapping("/counter/restore/{id}")
    public ResponseEntity<?> restoreContador(@PathVariable(name = "id")long id,@RequestParam("token") String token){
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
                    Contador c = repo.findById(id).get();

                    c.setActive(true);
                    repo.save(c);

                    status = HttpStatus.OK;
                    ans = "Contador restaurado con exito";
                }else{
                    status = HttpStatus.NOT_FOUND;
                    ans = "No se encontro contador con id "+id;
                }
            }
        }else{
            status = HttpStatus.UNAUTHORIZED;
            ans = "Error de sesion";
        }

        return ResponseEntity.status(status).body(ans);
    }
}
