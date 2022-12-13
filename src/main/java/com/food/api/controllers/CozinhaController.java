package com.food.api.controllers;

import com.food.api.assembler.CozinhaInputDisassembler;
import com.food.api.assembler.CozinhaModelAssembler;
import com.food.api.model.input.CozinhaInput;
import com.food.api.model.models.CozinhaModel;
import com.food.domain.model.Cozinha;
import com.food.domain.services.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/cozinhas")
@RestController
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService service;
    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaModel> listar() {
        List<Cozinha> todasCozinhas = service.listar();

        return cozinhaModelAssembler.toCollectionModel(todasCozinhas);
    }




    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = service.buscarCozinha(cozinhaId);
        return cozinhaModelAssembler.toModel(cozinha);
    }




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = service.salvar(cozinha);

        return cozinhaModelAssembler.toModel(cozinha);
    }


    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable Long cozinhaId,
                                  @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = service.buscarCozinha(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = service.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaAtual);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cozinhaId}")
    public void deletar(@PathVariable("cozinhaId") Long id) {
        service.remover(id);
    }
}
