package com.food.infrastructure.repositoryes;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Estado;
import com.food.domain.repositoryes.EstadoRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public class EstadoRepositoryImpl implements EstadoRepository {

    private EntityManager manager;


    @Override
    public List<Estado> listar() {
        return  manager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        return manager.find(Estado.class, id);
    }

    @Transactional
    @Override
    public Estado salvar(Estado estado) {
         return manager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Estado estado) {
        estado = buscar(estado.getId());
        manager.remove(estado);
    }
}
