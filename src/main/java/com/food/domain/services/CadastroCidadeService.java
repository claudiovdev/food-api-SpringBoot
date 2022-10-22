package com.food.domain.services;

import com.food.domain.exceptions.EntidadeEmUsoException;
import com.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repositoryes.CidadeRepository;
import com.food.domain.repositoryes.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Cidade buscar(Long cidadeId){
        try {
            return cidadeRepository.buscar(cidadeId);

        }catch (EmptyResultDataAccessException e){
            throw new EntidadeEmUsoException(
                    String.format("Não existe cadastro de cidade com o codigo %d",cidadeId)
            );
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Cidade de codigo %d não pode ser removido pois está em uso")
            );
        }

    }

}
