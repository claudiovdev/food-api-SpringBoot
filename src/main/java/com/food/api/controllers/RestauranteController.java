package com.food.api.controllers;

import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Restaurante;
import com.food.domain.repositoryes.RestauranteRepository;
import com.food.domain.services.CadastroRestauranteService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return restauranteRepository.listar();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id){
        Restaurante restaurante = restauranteRepository.buscar(id);

        if(restaurante != null){
            return ResponseEntity.ok(restaurante);
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
           Restaurante  restauranteatual = restauranteRepository.buscar(id);

           if(restauranteatual != null){
               BeanUtils.copyProperties(restaurante, restauranteatual, "id");

               cadastroRestauranteService.salvar(restauranteatual);
               return ResponseEntity.status(HttpStatus.CREATED).body("Restaurante atualizado com sucesso");
           }
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante não encontrado!");

       }catch (EntidadeNaoEncontradaException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }




    }

}
