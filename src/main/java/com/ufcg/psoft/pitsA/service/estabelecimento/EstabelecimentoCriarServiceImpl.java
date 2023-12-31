package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPostDTO;
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
    public Estabelecimento salvar(EstabelecimentoPostDTO estabelecimentoSave) {
        Estabelecimento estabelecimento =
                Estabelecimento.builder()
                        .codigoAcesso(estabelecimentoSave.getCodigoAcesso())
                        .build();

        return estabelecimentoRepository.save(estabelecimento);
    }
}
