package com.food.domain.repositoryes;

import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;

import java.util.List;

public interface CidadeRepository {
    List<Cidade> listar();
    Cidade buscar(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Cidade cidade);
}
