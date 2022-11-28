package com.food.api.domain.services;

import com.food.api.domain.exceptions.EntidadeEmUsoException;
import com.food.api.domain.exceptions.EntidadeNaoEncontradaException;
import com.food.api.domain.model.Estado;
import com.food.api.domain.repositoryes.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com o codigo %d";
    public static final String MSG_ESTADO_EM_USO = "Estado de codigo %d não pode ser excluisa, pois está em uso";
    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    }

   public void remover(Long id){
        try {
            buscarEstado(id);
            estadoRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id)
            );
        }
   }

   public Estado buscarEstado(Long id){
        return estadoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO,id)));
   }
}
