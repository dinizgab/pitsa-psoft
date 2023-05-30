package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.model.pedido.EstadoPedido;

@FunctionalInterface
public interface PedidoPatchEstadoService {
    PedidoReadResponseDTO alteraEstado(Long pedidoId, EstadoPedido estado);
}
