package com.food.domain.services;

import com.food.domain.exceptions.EntidadeEmUsoException;
import com.food.domain.exceptions.EstadoNaoEncontradaException;
import com.food.domain.model.Estado;
import com.food.domain.repositoryes.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado de codigo %d não pode ser excluisa, pois está em uso";
    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @Transactional
    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    }

    @Transactional
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
        return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradaException(id));
   }
}
