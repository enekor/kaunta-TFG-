package com.kunta.kaunta_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * devuelve un codigo 200 si la api es accesible
     * @return codigo 200
     */
    @GetMapping("test")
    public ResponseEntity<?> testConection(){
        return  ResponseEntity.status(HttpStatus.OK).body("Estamos activos!!");
    }
}
