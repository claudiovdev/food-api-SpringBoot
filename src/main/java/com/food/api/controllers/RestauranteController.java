package com.food.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.api.domain.exceptions.NegocioException;
import com.food.api.domain.services.CadastroRestauranteService;
import com.food.api.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.api.domain.model.Restaurante;
import com.food.api.domain.repositoryes.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {


    @Autowired
    private CadastroRestauranteService service;

    private RestauranteRepository restauranteRepository;


    @GetMapping
    public List<Restaurante> listar(){
        return service.listar();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable("restauranteId") Long id){
      return  service.buscar(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante salvar(@RequestBody Restaurante restaurante){
          try {
              return service.salvar(restaurante);
          }catch (EntidadeNaoEncontradaException e) {
              throw new NegocioException(e.getMessage());
          }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable("restauranteId") Long id, @RequestBody Restaurante restaurante){
           Restaurante restauranteAtual = buscar(id);
           BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produto");
           try {
               return service.salvar(restauranteAtual);
           }catch (EntidadeNaoEncontradaException e){
               throw new NegocioException(e.getMessage());
           }
    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = buscar(restauranteId);
        merge(campos, restauranteAtual);
        return atualizar(restauranteId, restauranteAtual);
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
}
