package com.food.api.controllers;



import com.food.api.assembler.CidadeInputDisassembler;
import com.food.api.assembler.CidadeModelAssembler;
import com.food.api.model.input.CidadeInput;
import com.food.api.model.models.CidadeModel;
import com.food.domain.exceptions.CozinhaNaoEncontradaException;
import com.food.domain.exceptions.EstadoNaoEncontradaException;
import com.food.domain.exceptions.NegocioException;
import com.food.domain.model.Cidade;
import com.food.domain.services.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/cidades")
@RestController
public class CidadeController {

    @Autowired
    CadastroCidadeService service;
    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;




    @GetMapping
    public List<CidadeModel> listar() {
        List<Cidade> todasCidades = service.listar();

        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = service.salvar(cidade);

            return cidadeModelAssembler.toModel(cidade);
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        Cidade cidade = service.buscarCidade(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("id") Long id) {
        service.excluir(id);
    }


    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = service.buscarCidade(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = service.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


}
