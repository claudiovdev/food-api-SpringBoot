package com.food.api.domain.services;

import com.food.api.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.api.domain.model.Cozinha;
import com.food.api.domain.model.Restaurante;
import com.food.api.domain.repositoryes.CozinhaRepository;
import com.food.api.domain.repositoryes.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> listar(){return  restauranteRepository.findAll();}

    private void remover(Long id){
        try {
            restauranteRepository.deleteById(id);

        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de restaurante com código %d", id));
        }
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

        if (cozinha.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com codigo %d", cozinhaId)
            );
        }

        restaurante.setCozinha(cozinha.get());
        return restauranteRepository.save(restaurante);
    }
}
