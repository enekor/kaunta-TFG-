package com.kunta.kaunta_api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.kunta.kaunta_api.model.Login;
import com.kunta.kaunta_api.reporitory.LoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.kunta.kaunta_api.dto.ContadorCreateDTO;
import com.kunta.kaunta_api.dto.EditContadorDTO;
import com.kunta.kaunta_api.model.Contador;
import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.reporitory.ContadorRepository;
import com.kunta.kaunta_api.reporitory.GrupoRepository;
import com.kunta.kaunta_api.upload.StorageService;

import lombok.RequiredArgsConstructor;

import javax.security.auth.login.LoginContext;

@RestController
@RequiredArgsConstructor
public class ContadorController {
    
    private final ContadorRepository repo;
    private final LoginRepository lRepo;
    private final GrupoRepository gRepo;
    private final StorageService storageService;

    @GetMapping("/counter/inactives/{group}")
    public ResponseEntity<?> getAllActiveByGroupId(@PathVariable(name = "group")long id){
        HttpStatus status = HttpStatus.ACCEPTED;
        Object ans = null;

        if(gRepo.existsById(id)){
            Grupo g = gRepo.findById(id).get();
            List<Contador> lista = g.getCounters().stream().filter((c)->!c.isActive()).collect(Collectors.toList());
            ans = lista;
            status = HttpStatus.OK;
        }else{
            status = HttpStatus.NOT_FOUND;
            ans = "No hay grupo con id "+id;
        }

        return ResponseEntity.status(status).body(ans);
    }

    @PostMapping(value = "/counter/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCounter(@RequestPart("counter") ContadorCreateDTO contador, @RequestPart("img")MultipartFile file, @RequestPart("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);

            if(LocalDateTime.now().isAfter(login.getExpireDate())){
                status = HttpStatus.UNAUTHORIZED;
                ans = "La sesion ha expirado";

                lRepo.deleteById(login.getId());
            }else{
                String urlImage = null;

                if(!file.isEmpty()){
                    String imagen = storageService.store(file);
                    urlImage = MvcUriComponentsBuilder.fromMethodName(FicherosController.class,"serveFile",imagen,null).build().toUriString();
                }

                Contador c = new Contador();
                c.setActive(true);
                c.setCount(contador.getCount());
                c.setDescrition(contador.getDescription());
                c.setImage(urlImage);
                c.setName(contador.getName());
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
        }

        return ResponseEntity.status(status).body(ans);
    }

    @PostMapping("/counter/edit")
    public ResponseEntity<?> updateCounter(@RequestBody EditContadorDTO c, @RequestParam("token") String token){
        HttpStatus status = HttpStatus.ACCEPTED;
        String ans = "";

        if(lRepo.existsByToken(token)){
            Login login = lRepo.findByToken(token);
            if(LocalDateTime.now().isAfter(login.getExpireDate())){
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

    @DeleteMapping("/counter/delete/{id}")
    public ResponseEntity<?> deleteContador(@PathVariable(name = "id")long id, @RequestParam("token") String token){
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

    @PostMapping("/counter/restore/{id}")
    public ResponseEntity<?> restoreContador(@PathVariable(name = "id")long id,@RequestParam("token") String token){
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
