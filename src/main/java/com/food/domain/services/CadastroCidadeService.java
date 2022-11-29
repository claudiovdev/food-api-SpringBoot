package com.food.domain.services;

import com.food.domain.exceptions.CidadeNaoEncontradaException;
import com.food.domain.exceptions.EntidadeEmUsoException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repositoryes.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {

    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
   CadastroEstadoService service;


    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }


    public Cidade salvar(Cidade cidade){
        Long id = cidade.getEstado().getId();
        Estado estado = service.buscarEstado(id);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
        }


    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id));
        }
    }

    public Cidade buscarCidade(Long id){
        return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

}
