package com.food.api.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class  EntidadeNaoEncontradaException extends NegocioException{

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException (String message){
        super(message);
    }
}
