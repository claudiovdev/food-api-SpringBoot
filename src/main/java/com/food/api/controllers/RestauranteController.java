package com.food.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.api.assembler.RestauranteInputDisassembler;
import com.food.api.assembler.RestauranteModelAssembler;
import com.food.api.model.models.RestauranteModel;
import com.food.api.model.input.RestauranteInput;
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
    public RestauranteModel atualizar(@PathVariable("restauranteId") Long id, @RequestBody @Valid RestauranteInput restauranteInput){

           try {
               //        Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

               Restaurante restauranteAtual = service.buscarEntydade(id);
               restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
               //BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produto");
               return  restauranteModelAssembler.toModel(service.salvar(restauranteAtual));
           }catch (EntidadeNaoEncontradaException e){
               throw new NegocioException(e.getMessage());
           }
    }

    @PatchMapping("/{restauranteId}")
    public RestauranteModel atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = service.buscarEntydade(restauranteId);
        merge(campos, restauranteAtual);
        return atualizar(restauranteId, toInputObjetc(restauranteAtual));
    }
    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

    @GetMapping("/consulta-por-nome")
    public List<Restaurante> consultarPorNome(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        return service.consultarPorNome(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/consultar-por-frete-gratis")
    public List<Restaurante> consultarComFreteGratis(String nome){
       return service.consultarComFreteGratis(nome);
    }



    private Restaurante toModelEntity(RestauranteModel restauranteModel){
        Cozinha cozinhaModel = new Cozinha();
        cozinhaModel.setId(restauranteModel.getCozinha().getId());
        cozinhaModel.setNome(restauranteModel.getCozinha().getNome());

        Restaurante restaurante = new Restaurante();
        restaurante.setId(restaurante.getId());
        restaurante.setNome(restaurante.getNome());
        restaurante.setTaxaFrete(restaurante.getTaxaFrete());
        restaurante.setCozinha(cozinhaModel);
        return restaurante;
    }



    private RestauranteInput toInputObjetc(Restaurante restaurante){
        RestauranteInput restauranteInput = new RestauranteInput();
        BeanUtils.copyProperties(restaurante,restauranteInput);
        return restauranteInput;
    }
}
