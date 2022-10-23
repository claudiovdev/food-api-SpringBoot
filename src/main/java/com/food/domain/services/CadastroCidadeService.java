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
import java.util.Optional;

@Service
public class CadastroCidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;


    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }


    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estadoOptional = estadoRepository.findById(estadoId);

        if (estadoOptional.isEmpty()){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com codigo %d", estadoId)
            );}
        cidade.setEstado(estadoOptional.get());
        return cidadeRepository.save(cidade);
        }




    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com código %d", cidadeId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
        }
    }
}
