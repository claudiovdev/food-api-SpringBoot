package com.food.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
/*
* Desta forma eu usaria para adicionar um propriedade que não existe em algumas das classes correspondentes
*
* */

//        var modelMapper = new ModelMapper();
//
//        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
 //               .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        return new ModelMapper();
    }
}
