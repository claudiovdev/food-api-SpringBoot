package com.food.domain.services;

import com.food.domain.exceptions.CozinhaNaoEncontradaException;
import com.food.domain.exceptions.EntidadeEmUsoException;
import com.food.domain.repositoryes.CozinhaRepository;
import com.food.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCozinhaService {



    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Cozinha> listar(){return cozinhaRepository.findAll();}

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void remover(Long id){

        try {
            cozinhaRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, id));
        }
    }

    public Cozinha buscarCozinha(Long id){
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

}
