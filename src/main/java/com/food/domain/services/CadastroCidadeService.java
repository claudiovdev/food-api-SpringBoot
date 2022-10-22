package com.food.domain.services;

import com.food.domain.model.Cidade;
import com.food.domain.repositoryes.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    public List<Cidade> listar(){
        return cidadeRepository.listar();
    }

}
