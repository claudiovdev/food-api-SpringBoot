package com.food.api.controllers;

import com.food.domain.exceptions.EntidadeEmUsoException;
import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.repositoryes.CozinhaRepository;
import com.food.domain.services.CadastroCozinhaService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cozinhas")
@RestController
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)   //Com essa produces definimos que o retnoro será sempre em Json á nivel de método
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha>  buscar(@PathVariable("cozinhaId") Long id){

        Cozinha cozinha =   cozinhaRepository.buscar(id);

        if(cozinha != null){
           return  ResponseEntity.ok(cozinha);
        }

       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha salvar(@RequestBody Cozinha cozinha){
        return cadastroCozinhaService.salvar(cozinha);
    }


    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long id, @RequestBody Cozinha cozinha){

        Cozinha cozinhaAtual = cozinhaRepository.buscar(id);

        if (cozinhaAtual != null){
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            cadastroCozinhaService.salvar(cozinhaAtual);
           return  ResponseEntity.ok(cozinhaAtual);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> deletar(@PathVariable("cozinhaId") Long id) {
        try {
            cadastroCozinhaService.remover(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
}
