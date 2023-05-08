package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPatchEntregadorDTO;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoPatchEntregadorImpl implements EstabelecimentoPatchEntregador {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Estabelecimento alteraParcialmente(Long id, EstabelecimentoPatchEntregadorDTO estabelecimentoEntregadorDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);

        estabelecimento.setEntregadores(estabelecimentoEntregadorDTO.getEntregadores());

        return estabelecimentoRepository.save(estabelecimento);
    }
}
