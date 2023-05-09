package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.EstabelecimentoAprovaEntregadorDTO;
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

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticaCodigoAcessoService.autenticar(estabelecimento.getCodigoAcesso(), codigoAcesso);


        Optional<Entregador> entregadorAprovado = estabelecimento.getEntregadoresPendentes()
                .stream()
                .filter(entregador -> entregador.getId().equals(entregadorId))
                .findFirst();

        if (entregadorAprovado.isEmpty()) throw new EntregadorNaoEstaPendenteException();

        Entregador entregadorPresente = entregadorAprovado.get();

        estabelecimento.getEntregadoresPendentes().remove(entregadorPresente);
        estabelecimento.getEntregadoresAprovados().add(entregadorPresente);

        estabelecimentoRepository.save(estabelecimento);

        return modelMapper.map(entregadorAprovado, EntregadorReadDTO.class);
    }
}
