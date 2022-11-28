package com.food.api.controllers;

import com.food.api.domain.exceptions.CozinhaNaoEncontradaException;
import com.food.api.domain.exceptions.EntidadeEmUsoException;
import com.food.api.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.api.domain.exceptions.NegocioException;
import com.food.api.domain.model.Cidade;
import com.food.api.domain.repositoryes.CidadeRepository;
import com.food.api.domain.services.CadastroCidadeService;
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
    CadastroCidadeService service;

    @Autowired
    CidadeRepository cidadeRepository;

    @GetMapping
    public List<Cidade> listar(){
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody  Cidade cidade) {
        try {
            return service.salvar(cidade);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable("cidadeId") Long id){
        return service.buscarCidade(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }


    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id,
                                            @RequestBody Cidade cidade) {
        var cidadeAtual = buscar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return  service.salvar(cidadeAtual);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

    }
}
