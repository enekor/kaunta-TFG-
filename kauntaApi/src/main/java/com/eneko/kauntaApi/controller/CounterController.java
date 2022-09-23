package com.eneko.kauntaApi.controller;

import com.eneko.kauntaApi.dto.CounterDTO;
import com.eneko.kauntaApi.mapper.Mapper;
import com.eneko.kauntaApi.model.Counter;
import com.eneko.kauntaApi.repository.CounterRepository;
import com.eneko.kauntaApi.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CounterController {
    private final CounterRepository repo;
    private final GroupRepository groupRepo;

    @GetMapping("/counter/inactive/{groupId}")
    public ResponseEntity<?> getActiveCounters(@PathVariable(name = "groupId")long id){
        List<Counter> counters = repo.findAllByActive(false);
        var ret = counters.stream().filter(counter -> counter.getGroup().getId()==id).collect(Collectors.toList());
        return ResponseEntity.ok(counters);
    }

    @PostMapping("/counter/save")
    public ResponseEntity<?> saveCounter(@RequestBody CounterDTO dto){
        Counter counter = Mapper.getInstance().counterDtoToCounter(dto,groupRepo);
        Counter c = repo.save(counter);
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/counter/delete/{id}")
    public ResponseEntity<?> deleteCounter(@PathVariable(name = "id")long id){
        HttpStatus status = null;
        String mensaje = "";

        if(repo.existsById(id)){
            Counter c = repo.findById(id).get();
            c.setActive(false);
            status = HttpStatus.ACCEPTED; //202
            mensaje = "Borrado con exito";
        }else{
            status = HttpStatus.NOT_FOUND;
            mensaje = "No existe contador con id "+id;
        }
        return ResponseEntity.status(status).body(mensaje);
    }

    @PostMapping("/counter/restore/{id}")
    public ResponseEntity<?> restoreCounter(@PathVariable(name = "id")long id){
        HttpStatus status = null;
        String mensaje = "";

        if(repo.existsById(id)){
            Counter c = repo.findById(id).get();
            c.setActive(true);
            status = HttpStatus.ACCEPTED; //202
            mensaje = "Restaurado con exito";
        }else{
            status = HttpStatus.NOT_FOUND;
            mensaje = "No existe contador con id "+id;
        }
        return ResponseEntity.status(status).body(mensaje);
    }
}