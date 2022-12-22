package com.food.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.api.assembler.RestauranteInputDisassembler;
import com.food.api.assembler.RestauranteModelAssembler;
import com.food.api.model.models.RestauranteModel;
import com.food.api.model.input.RestauranteInput;
import com.food.domain.exceptions.CidadeNaoEncontradaException;
import com.food.domain.exceptions.CozinhaNaoEncontradaException;
import com.food.domain.exceptions.NegocioException;
import com.food.domain.model.Cozinha;
import com.food.domain.services.CadastroRestauranteService;
import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Restaurante;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {


    @Autowired
    private CadastroRestauranteService service;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;



    @GetMapping
    public List<RestauranteModel> listar(){

        return restauranteModelAssembler.toCollectionModel(service.listar());

    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable("restauranteId") Long id){
      Restaurante restaurante =   service.buscarEntydade(id);
        return restauranteModelAssembler.toModel(restaurante);

    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel salvar(@RequestBody @Valid RestauranteInput restauranteInput){
          try {
              Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

              return restauranteModelAssembler.toModel(service.salvar(restaurante));
          }catch (EntidadeNaoEncontradaException e) {
              throw new NegocioException(e.getMessage());
          }
    }



    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = service.buscarEntydade(restauranteId);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteModelAssembler.toModel(service.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @GetMapping("/consulta-por-nome")
    public List<Restaurante> consultarPorNome(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        return service.consultarPorNome(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/consultar-por-frete-gratis")
    public List<Restaurante> consultarComFreteGratis(String nome){
       return service.consultarComFreteGratis(nome);
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){
        service.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inaativar(@PathVariable Long restauranteId){
        service.inativar(restauranteId);
    }

    private RestauranteInput toInputObjetc(Restaurante restaurante){
        RestauranteInput restauranteInput = new RestauranteInput();
        BeanUtils.copyProperties(restaurante,restauranteInput);
        return restauranteInput;
    }



}
