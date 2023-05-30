package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoPatchEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoPatchPedidoEstadoServiceImpl implements EstabelecimentoPatchPedidoEstadoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    PedidoPatchEstadoService pedidoPatchEstadoService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    public PedidoReadResponseDTO alterarEstadoPedido(Long estabelecimentoId, PedidoReadBodyDTO patchDTO) {
        String codigoAcesso = patchDTO.getCodigoAcesso();
        Long pedidoId = patchDTO.getPedidoId();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), codigoAcesso);

        return pedidoPatchEstadoService.alteraEstado(pedidoId);
    }
}
