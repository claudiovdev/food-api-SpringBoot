package com.food.domain.services;

import com.food.domain.exceptions.*;
import com.food.domain.model.Cidade;
import com.food.domain.model.Cozinha;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Restaurante;
import com.food.domain.repositoryes.CidadeRepository;
import com.food.domain.repositoryes.FormaPagamentoRepository;
import com.food.domain.repositoryes.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<Restaurante> listar(){return  restauranteRepository.findAll();}

    @Transactional
    private void remover(Long id){
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();

        }catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradaException(id);
        }
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarCozinha(cozinhaId);
        Cidade cidade = cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restauranteAtual = buscarEntydade(restauranteId);
        restauranteAtual.ativar();

    }

    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restauranteAtual = buscarEntydade(restauranteId);
        restauranteAtual.inativar();

    }

    public Restaurante buscarEntydade(Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradaException(id));
    }

    public List<Restaurante> consultarComFreteGratis(String nome){
        return restauranteRepository.ComFreteGratis(nome);
    }

    public List<Restaurante> consultarPorNome(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        Restaurante restaurante = buscarEntydade(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
        restaurante.associar(formaPagamento);
    }

}
