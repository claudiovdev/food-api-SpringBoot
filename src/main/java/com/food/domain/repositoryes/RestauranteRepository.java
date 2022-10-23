package com.food.domain.repositoryes;


import com.food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries  {
    //@Query(" from Restaurante c  where c.nome like %:nome% and c.cozinha.id = :id")
    //List<Restaurante> consultarPorNome(String nome,@Param("id") Long cozinhaId);

    List<Restaurante> find(String nome,
                           BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
