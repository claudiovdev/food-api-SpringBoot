package com.food.jpa;

import com.food.FoodApplication;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repositoryes.CozinhaRepository;
import com.food.domain.repositoryes.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultarRestaurante {
    public static void main(String[] args) {
        ApplicationContext applicationContext =  new SpringApplicationBuilder(FoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        List<Restaurante> restaurantes  = restauranteRepository.listar();

        for (Restaurante restaurante : restaurantes){
            System.out.println(restaurante.getNome());
        }

    }

}
