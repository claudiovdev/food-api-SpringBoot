package com.food.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NegocioException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NegocioException(String mensagem){
        super(mensagem);
    }

    public NegocioException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
