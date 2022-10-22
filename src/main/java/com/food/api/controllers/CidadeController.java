package com.food.api.controllers;

import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Cidade;
import com.food.domain.services.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cidades")
@RestController
public class CidadeController {

    @Autowired
    CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar(){
        return cadastroCidadeService.listar();
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody  Cidade cidade) {
        try {
            cidade = cadastroCidadeService.salvar(cidade);
           return  ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Object> buscar(@PathVariable("cidadeId") Long id){
        Cidade cidade = cadastroCidadeService.buscar(id);

        if(cidade != null){
           return  ResponseEntity.ok().body(cidade);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma cidade encontrada");
    }
}
