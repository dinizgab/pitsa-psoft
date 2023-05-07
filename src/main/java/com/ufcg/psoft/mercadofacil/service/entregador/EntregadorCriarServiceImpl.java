package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.mercadofacil.dto.EntregadorReadDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorCriarServiceImpl implements EntregadorCriarService {
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public EntregadorReadDTO salvar(EntregadorPostPutDTO entregadorSave) {
        Entregador entregador = modelMapper.map(entregadorSave, Entregador.class);

        Entregador result = entregadorRepository.save(entregador);

        return modelMapper.map(result, EntregadorReadDTO.class);
    }
}
