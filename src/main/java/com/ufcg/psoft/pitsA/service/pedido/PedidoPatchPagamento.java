package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.model.pedido.TipoPagamento;

@FunctionalInterface
public interface PedidoPatchPagamento {
    PedidoReadResponseDTO alterarPagamento(Long pedidoId, TipoPagamento tipoPagamento);
}
