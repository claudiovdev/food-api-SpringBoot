package com.food.api.controllers;

import com.food.api.assembler.EstadoInputDisassembler;
import com.food.api.assembler.EstadoModelAssembler;
import com.food.api.model.input.EstadoInput;
import com.food.api.model.models.EstadoModel;
import com.food.domain.services.CadastroEstadoService;
import com.food.domain.model.Estado;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/estados")
@RestController
public class EstadoController {
    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;


    @Autowired
    CadastroEstadoService service;


    @GetMapping
    public List<EstadoModel> listar() {
        List<Estado> todosEstados = service.listar();

        return estadoModelAssembler.toCollectionModel(todosEstados);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

        estado = service.salvar(estado);

        return estadoModelAssembler.toModel(estado);
    }

    @GetMapping("/{estadoId}")
    public EstadoModel buscar(@PathVariable Long estadoId) {
        Estado estado = service.buscarEstado(estadoId);

        return estadoModelAssembler.toModel(estado);
    }


    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estadoId") Long id){service.remover(id);}

    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(@PathVariable Long estadoId,
                                 @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = service.buscarEstado(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = service.salvar(estadoAtual);

        return estadoModelAssembler.toModel(estadoAtual);
    }

}
