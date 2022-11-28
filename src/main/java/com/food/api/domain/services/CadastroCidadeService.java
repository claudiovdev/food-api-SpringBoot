package com.food.api.domain.services;

import com.food.api.domain.exceptions.EntidadeEmUsoException;
import com.food.api.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.api.domain.model.Cidade;
import com.food.api.domain.model.Estado;
import com.food.api.domain.repositoryes.CidadeRepository;
import com.food.api.domain.repositoryes.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroCidadeService {

    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código %d";
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
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_CIDADE_NAO_ENCONTRADA, id));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id));
        }
    }

    public Cidade buscarCidade(Long id){
        return cidadeRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, id)));
    }

}
