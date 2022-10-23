package com.food.api.controllers;

import com.food.domain.exceptions.EntidadeEmUsoException;
import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Cidade;
import com.food.domain.repositoryes.CidadeRepository;
import com.food.domain.services.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/cidades")
@RestController
public class CidadeController {

    @Autowired
    CadastroCidadeService cadastroCidadeService;

    @Autowired
    CidadeRepository cidadeRepository;

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
        Optional<Cidade> cidade = cidadeRepository.findById(id);

        if(cidade.isPresent()){
           return  ResponseEntity.ok().body(cidade);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma cidade encontrada");
    }

    @DeleteMapping("/{cidadeAId}")
    public ResponseEntity<?> remover(@PathVariable Long cidadeAId) {
        try {
            cadastroCidadeService.excluir(cidadeAId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
    @PutMapping("/{cidadeId}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long cidadeId,
                                            @RequestBody Cidade cidade) {
        Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);

        if (cidadeAtual.isPresent()) {
            BeanUtils.copyProperties(cidade, cidadeAtual.get() , "id");

           Cidade cidadeSalva = cadastroCidadeService.salvar(cidadeAtual.get());
            return ResponseEntity.ok(cidadeSalva);
        }

        return ResponseEntity.notFound().build();
    }
}
