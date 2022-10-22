package com.food.api.controllers;

import com.food.domain.model.Estado;
import com.food.domain.repositoryes.EstadoRepository;
import com.food.domain.services.CadastroEstadoService;
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
    CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar(){
       return cadastroEstadoService.listar();
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado){
       estado = cadastroEstadoService.salvar(estado);
       return ResponseEntity.ok().body(estado);
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Object> buscar(@PathVariable("estadoId") Long id){
        Estado estado = cadastroEstadoService.buscar(id);
        if(estado != null){
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum estado encontrado");
    }


    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Object> remover(@PathVariable("estadoId") Long id){
        Estado estado = cadastroEstadoService.buscar(id);

        if(estado != null){
            cadastroEstadoService.remover(estado.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Estado deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum Estado encontrado");
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Object> atualizar(@PathVariable("estadoId") Long id,@RequestBody Estado estado){

        Estado estadoAtual = cadastroEstadoService.buscar(id);

        if (estadoAtual != null){
            BeanUtils.copyProperties(estado, estadoAtual, "id");
            estadoAtual = cadastroEstadoService.salvar(estadoAtual);
            return ResponseEntity.status(HttpStatus.OK).body(estadoAtual);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum está encontrado");
    }
}
