package com.food.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.food.api.model.moxin.CidadeMixin;
import com.food.api.model.moxin.CozinhaMixin;
import com.food.api.model.moxin.RestauranteMixin;
import com.food.domain.model.Cidade;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule  extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }

}
