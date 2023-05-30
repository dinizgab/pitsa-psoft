package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

@FunctionalInterface
public interface EstabelecimentoPatchPedidoEstadoService {
    PedidoReadResponseDTO alterarEstadoPedido(Long estabelecimentoId, PedidoReadBodyDTO patchDTO);
}
