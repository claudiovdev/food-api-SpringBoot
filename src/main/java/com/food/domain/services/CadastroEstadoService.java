package com.food.domain.services;

import com.food.domain.model.Estado;
import com.food.domain.repositoryes.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.listar();
    }

    public Estado salvar(Estado estado){
        return estadoRepository.salvar(estado);
    }

   public Estado buscar(Long id){
        return estadoRepository.buscar(id);
   }

   public void remover(Long id){
        estadoRepository.remover(id);
   }
}
