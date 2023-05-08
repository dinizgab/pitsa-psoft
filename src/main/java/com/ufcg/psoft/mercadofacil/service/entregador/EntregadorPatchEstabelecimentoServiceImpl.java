package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoEntregadorDTO;
import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoListarService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoPatchEntregador;
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
    AutenticaEmpregadoService autenticaEmpregadoService;
    @Autowired
    ModelMapper modelMapper;

    // TODO - Lista de entregadores pendentes em estabelecimento, e quando aprovar eles, tira da lista de pendentes e adiciona na lista de aprovados
    // Quando for fazer uma operaçao sob um entregador de um estabelecimento, verifica se ele ta na lista de aprovados

    @Override
    public Entregador alteraParcialmente(Long entregadorId, Long estabelecimentoId, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(EntregadorNaoExisteException::new);
        autenticaEmpregadoService.autenticar(entregador.getCodigoAcesso(), codigoAcesso);

        Estabelecimento estabelecimento = estabelecimentoListarService.listar(estabelecimentoId).get(0);

        entregador.getEstabelecimentos().add(estabelecimento);
        estabelecimento.getEntregadores().add(entregador);

        estabelecimentoPatchEntregador.alteraParcialmente(estabelecimentoId, modelMapper.map(estabelecimento, EstabelecimentoEntregadorDTO.class));

        return entregador;
    }
}
