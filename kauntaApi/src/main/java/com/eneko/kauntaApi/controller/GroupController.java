package com.eneko.kauntaApi.controller;

import com.eneko.kauntaApi.dto.GroupDTO;
import com.eneko.kauntaApi.mapper.Mapper;
import com.eneko.kauntaApi.model.Group;
import com.eneko.kauntaApi.repository.CounterRepository;
import com.eneko.kauntaApi.repository.GroupRepository;
import com.eneko.kauntaApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupRepository repo;
    private final UserRepository userRepo;
    private final CounterRepository counterRepo;

    @GetMapping("/active/all")
    public ResponseEntity<?> getActiveGroups(@PageableDefault(page = 0,size = 200) Pageable pageable){
        Page<Group> groups = repo.findAllByActive(pageable,true);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/inactive/all")
    public ResponseEntity<?> getInactiveGroups(@PageableDefault(page = 0,size = 200)Pageable pageable){
        Page<Group> groups = repo.findAllByActive(pageable,false);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveGroup(@RequestBody GroupDTO dto){
        Group group = Mapper.getInstance().groupDtoToGroup(dto,userRepo,counterRepo);
        Group g = repo.save(group);
        return ResponseEntity.ok(g);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable(name = "id") long id){

        HttpStatus status = null;
        String mensaje = "";

        if(repo.existsById(id)){
            Group g = repo.findById(id).get();
            g.setActive(false);
            status = HttpStatus.ACCEPTED;//202
            mensaje = "Borado con exito";
        }else{
            status = HttpStatus.NOT_FOUND;
            mensaje = "No existe frupo con id "+id;
        }
        return ResponseEntity.status(status).body(mensaje);
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<?> restoreGroup(@PathVariable(name = "id")long id){

        HttpStatus status = null;
        String mensaje = "";

        if(repo.existsById(id)){
            Group g = repo.findById(id).get();
            g.setActive(true);
            status = HttpStatus.ACCEPTED;//202
            mensaje = "Restaurado con exito";
        }else{
            status = HttpStatus.NOT_FOUND;
            mensaje = "No existe frupo con id "+id;
        }
        return ResponseEntity.status(status).body(mensaje);
    }
}