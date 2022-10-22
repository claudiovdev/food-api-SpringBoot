package com.food.infrastructure.repositoryes;

import com.food.domain.model.Cozinha;
import com.food.domain.repositoryes.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar(){
        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);

        return  query.getResultList();
    }

    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return manager.merge(cozinha);
    }

    @Override
    @Transactional
    public void remover(Long id) {
       Cozinha cozinha = buscar(id);
       if(cozinha == null){
           throw new EmptyResultDataAccessException(1);
       }
        manager.remove(cozinha);
    }

    @Override
    public Cozinha buscar(Long id){
        return manager.find(Cozinha.class, id);
    }

}
