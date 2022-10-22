package com.food.domain.exceptions;

public class EntidadeEmUsoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoException(String message){
        super(message);
    }
}
