package com.food.domain.repositoryes;

import com.food.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome,
                           BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

     List<Restaurante> ComFreteGratis(String nome);
}
