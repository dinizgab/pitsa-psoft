package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.exception.estabelecimento.NenhumEntregadorDisponivelException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.pedido.EstadoPedido;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoAtualizarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoPatchPedidoEstadoServiceImpl implements EstabelecimentoPatchPedidoEstadoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    PedidoAtualizarService pedidoAtualizarService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    public PedidoReadResponseDTO alterarEstadoPedido(Long estabelecimentoId, PedidoReadBodyDTO patchDTO) {
        String codigoAcesso = patchDTO.getCodigoAcesso();
        Long pedidoId = patchDTO.getPedidoId();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), codigoAcesso);

        Pedido pedido = estabelecimento.encontraPedido(pedidoId);
        Entregador entregador;
        try {
            entregador = estabelecimento.proximoEntregador();

            pedido.setEstadoEmRota(entregador);
        } catch (NenhumEntregadorDisponivelException e) {
            pedido.setEstadoPronto();
        }

        return pedidoAtualizarService.atualizarPedido(pedido);
    }
}
