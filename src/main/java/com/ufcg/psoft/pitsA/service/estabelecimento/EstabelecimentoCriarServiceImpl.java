package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.EstabelecimentoPutPostDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
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
