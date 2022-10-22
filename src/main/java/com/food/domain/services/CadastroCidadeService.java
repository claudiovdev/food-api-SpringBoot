package com.food.domain.services;

import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repositoryes.CidadeRepository;
import com.food.domain.repositoryes.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class CadastroCidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;


    public List<Cidade> listar(){
        return cidadeRepository.listar();
    }


    public Cidade salvar(Cidade cidade){
        Estado estado = estadoRepository.buscar(cidade.getEstado().getId());

        if (estado == null){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de Estado com o codigo: %d", cidade.getEstado().getId())
            );
        }
        cidade.setEstado(estado);
        return cidadeRepository.salvar(cidade);
    }

    public Cidade buscar(Long id){
        return cidadeRepository.buscar(id);
    }

}
