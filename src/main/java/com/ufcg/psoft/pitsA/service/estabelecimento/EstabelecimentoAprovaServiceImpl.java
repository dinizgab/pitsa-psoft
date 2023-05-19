package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoAprovaEntregadorDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.StatusAprovacao;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoEstaPendenteException;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstabelecimentoAprovaServiceImpl implements EstabelecimentoAprovaService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticaCodigoAcessoService;
    @Autowired
    ModelMapper modelMapper;

    public EntregadorReadDTO aprova(Long estabelecimentoId, EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO) {
        Long entregadorId = estabelecimentoDTO.getEntregadorId();
        String codigoAcesso = estabelecimentoDTO.getCodigoAcesso();
        StatusAprovacao aprovacao = estabelecimentoDTO.getAprovar();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticaCodigoAcessoService.autenticar(estabelecimento.getCodigoAcesso(), codigoAcesso);

        Entregador entregador = estabelecimento.getEntregadoresPendentes()
                .stream()
                .filter(e -> e.getId().equals(entregadorId))
                .findFirst()
                .orElseThrow(EntregadorNaoEstaPendenteException::new);

        if (aprovacao.isAprovado()) {
            estabelecimento.getEntregadoresAprovados().add(entregador);
        }

        // TODO - Corrigir bug === o entregador nao ta sendo removido da lista de pendentes
        estabelecimento.getEntregadoresPendentes().removeIf(e -> e.getId().equals(entregadorId));

        System.out.println(estabelecimento);
        estabelecimentoRepository.save(estabelecimento);

        return modelMapper.map(entregador, EntregadorReadDTO.class);
    }
}
