package com.food.core.modelMapper;

import com.food.api.model.models.EnderecoModel;
import com.food.api.model.models.RestauranteModel;
import com.food.domain.model.Endereco;
import com.food.domain.model.Restaurante;
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

        var modelMapper = new ModelMapper();

//        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//               .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setTaxaFrete);




        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoModel.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setNomeEstado(value));

        return new ModelMapper();
    }
}
