package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.ConfirmarPagamentoDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

@FunctionalInterface
public interface ConfirmarPagamentoService {
    PedidoReadResponseDTO confirmarPagamento(Long pedidoId, ConfirmarPagamentoDTO confirmarPagamentoDTO);
}
