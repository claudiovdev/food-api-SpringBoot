package com.food.api.controllers;

import com.food.domain.model.Cozinha;
import com.food.domain.services.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cozinhas")
@RestController
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)   //Com essa produces definimos que o retnoro será sempre em Json á nivel de método
    public List<Cozinha> listar(){
        return service.listar();
    }




    @GetMapping("/{cozinhaId}")
    public Cozinha  buscar(@PathVariable("cozinhaId") Long id){
       return service.buscarCozinha(id);
    }




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha salvar(@RequestBody Cozinha cozinha){
        return service.salvar(cozinha);
    }


    @PutMapping("/{cozinhaId}")
    public Cozinha atualizar(@PathVariable("cozinhaId") Long id, @RequestBody Cozinha cozinha){
         Cozinha cozinhaAtual = service.buscarCozinha(id);
         BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
         Cozinha cozinhaSalva =  service.salvar(cozinhaAtual);
         return cozinhaSalva;
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cozinhaId}")
    public void deletar(@PathVariable("cozinhaId") Long id) {
        service.remover(id);
    }
}
