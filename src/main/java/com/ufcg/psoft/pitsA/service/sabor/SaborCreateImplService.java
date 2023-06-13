package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborPostDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoAdicionaSaborService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborCreateImplService implements SaborCreateService {
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoAdicionaSaborService estabelecimentoAdicionaSabor;
    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public SaborReadDTO create(Long estabelecimentoId, SaborPostDTO saborDTO) {
        Estabelecimento estabelecimento = estabelecimentoListarService.listar(estabelecimentoId).get(0);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), saborDTO.getCodigoAcesso());

        Sabor sabor = saborRepository.save(modelMapper.map(saborDTO, Sabor.class));

        estabelecimentoAdicionaSabor.adicionaSabor(estabelecimentoId, sabor);
        return modelMapper.map(sabor, SaborReadDTO.class);
    }
}

