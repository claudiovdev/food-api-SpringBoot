package com.food.domain.services;

import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repositoryes.CozinhaRepository;
import com.food.domain.repositoryes.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.cozinha.getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if (cozinha == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com codigo %d", cozinhaId)
            );
        }

        restaurante.setCozinha(cozinha);
        return restauranteRepository.salvar(restaurante);
    }
}
