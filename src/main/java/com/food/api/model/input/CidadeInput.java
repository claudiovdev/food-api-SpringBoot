package com.food.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInput estado;
}
