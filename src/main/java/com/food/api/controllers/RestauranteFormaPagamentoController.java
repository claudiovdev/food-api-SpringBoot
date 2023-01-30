package com.food.api.controllers;

import com.food.api.assembler.FormaPagamentoModelAssembler;
import com.food.api.assembler.RestauranteInputDisassembler;
import com.food.api.assembler.RestauranteModelAssembler;
import com.food.api.model.input.RestauranteInput;
import com.food.api.model.models.FormaPagamentoModel;
import com.food.api.model.models.RestauranteModel;
import com.food.domain.exceptions.CidadeNaoEncontradaException;
import com.food.domain.exceptions.CozinhaNaoEncontradaException;
import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.exceptions.NegocioException;
import com.food.domain.model.Restaurante;
import com.food.domain.services.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@RestController
public class RestauranteFormaPagamentoController {


    @Autowired
    private CadastroRestauranteService service;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar( @PathVariable Long restauranteId){
        Restaurante restaurante = service.buscarEntydade(restauranteId);
        return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento());

    }

    @PutMapping("/{formaPagamentoId}")
    public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        service.associarFormaPagamento(restauranteId, formaPagamentoId);
    }


}
