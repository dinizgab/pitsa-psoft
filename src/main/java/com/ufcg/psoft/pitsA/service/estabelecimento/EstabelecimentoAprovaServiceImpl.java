package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.EstabelecimentoAprovaEntregadorDTO;
import com.ufcg.psoft.pitsA.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAprovaServiceImpl implements EstabelecimentoAprovaService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    public EntregadorReadDTO aprova(Long estabelecimentoId, EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO) {
        Long entregadorId = estabelecimentoDTO.getEntregadorId();
        String codigoAcesso = estabelecimentoDTO.getCodigoAcesso();

        // TODO - Adicionar a validacao do codigo de acesso do estabelecimento
        // TODO - Criacao do erro se o entregador nao estiver na lista de pendencia

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);

        Entregador entregadorAprovado = estabelecimento.getEntregadoresPendentes()
                .stream()
                .filter(entregador -> entregador.getId() == entregadorId)
                .findFirst().get();

        estabelecimento.getEntregadoresPendentes().remove(entregadorAprovado);
        estabelecimento.getEntregadoresAprovados().add(entregadorAprovado);

        estabelecimentoRepository.save(estabelecimento);

        return modelMapper.map(entregadorAprovado, EntregadorReadDTO.class);
    }
}
