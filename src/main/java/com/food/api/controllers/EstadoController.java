package com.food.api.controllers;

import com.food.domain.exceptions.EntidadeEmUsoException;
import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Estado;
import com.food.domain.repositoryes.EstadoRepository;
import com.food.domain.services.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/estados")
@RestController
public class EstadoController {


    @Autowired
    CadastroEstadoService cadastroEstadoService;
    @Autowired
    EstadoRepository estadoRepository;

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
        Optional<Estado> estado = estadoRepository.findById(id);
        if(estado.isPresent()){
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum estado encontrado");
    }


    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Object> remover(@PathVariable("estadoId") Long id){
        try {
            cadastroEstadoService.remover(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Object> atualizar(@PathVariable("estadoId") Long id,@RequestBody Estado estado){

        Optional<Estado> estadoAtual = estadoRepository.findById(id);

        if (estadoAtual.isPresent()){
            BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
          Estado  estadoSalvo = cadastroEstadoService.salvar(estadoAtual.get());
            return ResponseEntity.status(HttpStatus.OK).body(estadoSalvo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum está encontrado");
    }
}
