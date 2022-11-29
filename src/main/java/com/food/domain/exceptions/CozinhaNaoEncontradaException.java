package com.food.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public  class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String message){
        super(message);
    }

    public CozinhaNaoEncontradaException(Long id){
        this(String.format("Não existe um cadastro de cozinha com o codigo %d", id));
    }
}
