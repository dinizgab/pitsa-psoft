package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.mercadofacil.dto.EntregadorReadDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoInvalidoException;
import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAtualizarServiceImpl implements EntregadorAtualizarService {
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EntregadorReadDTO atualizar(Long id, EntregadorPostPutDTO entregadorAtualizado) throws CodigoAcessoInvalidoException {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);

        if (!entregador.getCodigoAcesso().equals(entregadorAtualizado.getCodigoAcesso())) {
            throw new CodigoAcessoInvalidoException();
        }

        modelMapper.map(entregadorAtualizado, entregador);
        Entregador resultado = entregadorRepository.save(entregador);

        return modelMapper.map(resultado, EntregadorReadDTO.class);
    }
}
