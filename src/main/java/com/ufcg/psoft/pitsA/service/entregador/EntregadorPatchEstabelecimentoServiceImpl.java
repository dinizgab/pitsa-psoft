package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPatchEntregadorDTO;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
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
    AutenticaCodigoAcessoService autenticaEmpregadoService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Entregador alteraParcialmente(Long entregadorId, EntregadorPatchEstabelecimentoDTO entregadorDTO) {
        String codigoAcesso = entregadorDTO.getCodigoAcesso();
        Long estabelecimentoId = entregadorDTO.getEstabelecimentoId();

        Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(EntregadorNaoExisteException::new);
        autenticaEmpregadoService.autenticar(entregador.getCodigoAcesso(), codigoAcesso);

        Estabelecimento estabelecimento = estabelecimentoListarService.listar(estabelecimentoId).get(0);

        entregador.getEstabelecimentos().add(estabelecimento);
        estabelecimento.getEntregadoresPendentes().add(entregador);

        EstabelecimentoPatchEntregadorDTO estabelecimentoEntregadorDTO = EstabelecimentoPatchEntregadorDTO.builder()
                .entregadores(estabelecimento.getEntregadoresPendentes())
                .build();

        estabelecimentoPatchEntregador.alteraParcialmente(estabelecimentoId, estabelecimentoEntregadorDTO);

        return entregador;
    }
}
