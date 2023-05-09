package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPutPostDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoCriarServiceImpl implements EstabelecimentoCriarService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Estabelecimento salvar(EstabelecimentoPutPostDTO estabelecimentoSave) {
        Estabelecimento estabelecimento = modelMapper.map(estabelecimentoSave, Estabelecimento.class);

        return estabelecimentoRepository.save(estabelecimento);
    }
}
