package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPatchDispDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.sabor.SaborPatchDisponibilidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoPatchDispSaborServiceImpl implements EstabelecimentoPatchDispSaborService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    SaborPatchDisponibilidade saborPatchDisponibilidade;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public SaborReadDTO alteraDisponibilidade(Long estabelecimentoId, EstabelecimentoPatchDispDTO estabelecimentoPatchDispDTO) {
        Long saborId = estabelecimentoPatchDispDTO.getSaborId();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), estabelecimentoPatchDispDTO.getCodigoAcesso());

        return modelMapper.map(saborPatchDisponibilidade.alteraDisponibilidade(saborId), SaborReadDTO.class);
    }
}
