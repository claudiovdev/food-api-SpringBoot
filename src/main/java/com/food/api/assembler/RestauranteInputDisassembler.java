package com.food.api.assembler;

import com.food.api.model.input.RestauranteInput;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {
    public Restaurante toDomainObject(RestauranteInput restauranteInput){
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }
}
