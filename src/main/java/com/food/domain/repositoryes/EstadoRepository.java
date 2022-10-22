package com.food.domain.repositoryes;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {
    List<Estado> listar();
    Estado buscar(Long id);
    Estado salvar(Estado estado);
    void remover(Long id);
}
