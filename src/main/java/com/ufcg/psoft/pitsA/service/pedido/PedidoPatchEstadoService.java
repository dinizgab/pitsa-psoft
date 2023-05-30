package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

@FunctionalInterface
public interface PedidoPatchEstadoService {
    PedidoReadResponseDTO alteraEstado(Long pedidoId);
}
