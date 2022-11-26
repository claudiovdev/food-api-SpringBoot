package com.food.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;


    @GetMapping
    public List<Restaurante> listar(){
        return cadastroRestauranteService.listar();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id){
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        if(restaurante.isPresent()){
            return ResponseEntity.ok(restaurante.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> salvar(@RequestBody Restaurante restaurante){

        try {
          restaurante  = cadastroRestauranteService.salvar(restaurante);
          return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<Object> atualizar(@PathVariable("restauranteId") Long id, @RequestBody Restaurante restaurante){

       try {
           Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);

           if(restauranteAtual.isPresent()){
               BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formasPagamento", "endereco", "dataCadastro", "produto");

               cadastroRestauranteService.salvar(restauranteAtual.get());
               return ResponseEntity.status(HttpStatus.CREATED).body("Restaurante atualizado com sucesso");
           }
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante não encontrado!");

       }catch (EntidadeNaoEncontradaException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

        if (restauranteAtual.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtual.get());

        return atualizar(restauranteId, restauranteAtual.get());
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
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/consultar-por-frete-gratis")
    public List<Restaurante> consultarComFreteGratis(String nome){
       return restauranteRepository.ComFreteGratis(nome);
    }
}
