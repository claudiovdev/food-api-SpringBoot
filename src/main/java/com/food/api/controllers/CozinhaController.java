package com.food.api.controllers;

import com.food.domain.model.Cozinha;
import com.food.domain.repositoryes.CozinhaRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/cozinhas")
@RestController
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)   //Com essa produces definimos que o retnoro será sempre em Json á nivel de método
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha>  buscar(@PathVariable("cozinhaId") Long id){

        Cozinha cozinha =   cozinhaRepository.buscar(id);

        if(cozinha != null){
            ResponseEntity.ok(cozinha);
        }

       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
