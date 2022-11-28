package com.food.api.domain.services;

import com.food.api.domain.exceptions.EntidadeEmUsoException;
import com.food.api.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.api.domain.exceptions.RestauranteNaoEncontradaException;
import com.food.api.domain.model.Cozinha;
import com.food.api.domain.model.Restaurante;
import com.food.api.domain.repositoryes.CozinhaRepository;
import com.food.api.domain.repositoryes.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    public List<Restaurante> listar(){return  restauranteRepository.findAll();}

    private void remover(Long id){
        try {
            restauranteRepository.deleteById(id);

        }catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradaException(id);
        }
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cozinhaService.buscarCozinha(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscar(Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradaException(id));
    }

    public List<Restaurante> consultarComFreteGratis(String nome){
        return restauranteRepository.ComFreteGratis(nome);
    }

    public List<Restaurante> consultarPorNome(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

}
