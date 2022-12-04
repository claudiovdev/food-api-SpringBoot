package com.food.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompriensivel", "Mensagem incompreensivel"),
    RECURSO_NAO_ENCONTRADO("/entidade-nao_encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação da regra de negócio"),
    PARAMETRO_INVALIDO("/parametro-invalido","Parametro invalido" ),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"), DADOS_INVALIDOS("/dados-invalidos","Dados invalidos" );


    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "http://food.com.br" + path;
        this.title  = title;
    }
}
