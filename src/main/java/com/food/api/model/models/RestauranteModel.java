package com.food.api.model.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;
    private Integer ativo;
    private EnderecoModel endereco;
}
