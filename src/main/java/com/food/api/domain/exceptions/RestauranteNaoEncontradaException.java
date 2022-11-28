package com.food.api.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public  class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradaException(String message){
        super(message);
    }

    public RestauranteNaoEncontradaException(Long id){
        this(String.format("Não existe um cadastro de restaurante com o codigo %d", id));
    }
}
