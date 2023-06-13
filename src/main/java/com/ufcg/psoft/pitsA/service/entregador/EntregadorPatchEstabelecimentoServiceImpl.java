package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoPatchEntregador;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorPatchEstabelecimentoServiceImpl implements EntregadorPatchEstabelecimentoService {
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    EstabelecimentoPatchEntregador estabelecimentoPatchEntregador;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Entregador alteraParcialmente(Long entregadorId, EntregadorPatchEstabelecimentoDTO entregadorDTO) {
        String codigoAcesso = entregadorDTO.getCodigoAcesso();
        Long estabelecimentoId = entregadorDTO.getEstabelecimentoId();

        Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(EntregadorNaoExisteException::new);
        autenticador.autenticar(entregador.getCodigoAcesso(), codigoAcesso);

        estabelecimentoPatchEntregador.alteraParcialmente(estabelecimentoId, entregador);
        return entregador;
    }
}
