package com.food.api.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public  class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String message){
        super(message);
    }

    public CidadeNaoEncontradaException(Long id){
        this(String.format("Não existe um cadastro de cidade com o codigo %d", id));
    }
}
