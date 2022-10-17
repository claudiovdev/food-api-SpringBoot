package com.food.jpa;

import com.food.domain.model.Cozinha;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar(){
        TypedQuery<Cozinha>  query = manager.createQuery("from Cozinha", Cozinha.class);

       return  query.getResultList();
    }

    @Transactional
    public Cozinha adicionarCozinha(Cozinha cozinha){
        return manager.merge(cozinha);
    }

    public Cozinha buscar(Long id){
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    public void atualizarCozinha(Cozinha cozinha, Long id){
        cozinha.setId(id);
        manager.merge(cozinha);
    }

    @Transactional
    public void deletar(Cozinha cozinha){
        cozinha = buscar(cozinha.getId());
        manager.remove(cozinha);
    }

}
