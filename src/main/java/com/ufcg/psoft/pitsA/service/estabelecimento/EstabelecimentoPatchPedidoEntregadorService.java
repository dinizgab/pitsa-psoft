package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoPatchEntregadorDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

@FunctionalInterface
public interface EstabelecimentoPatchPedidoEntregadorService {
    PedidoReadResponseDTO alterarEntregador(Long estabelecimentoId, PedidoPatchEntregadorDTO patchBody);
}
