package com.food.api.controllers;

import com.food.domain.services.CadastroEstadoService;
import com.food.domain.model.Estado;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/estados")
@RestController
public class EstadoController {


    @Autowired
    CadastroEstadoService service;


    @GetMapping
    public List<Estado> listar(){
       return service.listar();
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado){
       estado = service.salvar(estado);
       return ResponseEntity.ok().body(estado);
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable("estadoId") Long id){
        return service.buscarEstado(id);
    }


    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estadoId") Long id){service.remover(id);}

    @PutMapping("/{estadoId}")
    public ResponseEntity<Object> atualizar(@PathVariable("estadoId") Long id,@RequestBody Estado estado){

        Estado estadoAtual = service.buscarEstado(id);
            BeanUtils.copyProperties(estado, estadoAtual, "id");
            Estado  estadoSalvo = service.salvar(estadoAtual);
            return ResponseEntity.status(HttpStatus.OK).body(estadoSalvo);
        }

}
