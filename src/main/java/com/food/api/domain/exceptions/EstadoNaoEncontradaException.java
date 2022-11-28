package com.food.api.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public  class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradaException(String message){
        super(message);
    }

    public EstadoNaoEncontradaException(Long id){
        this(String.format("Não existe um cadastro de estado com o codigo %d", id));
    }
}
