package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoConfirmaEntregaDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

@FunctionalInterface
public interface ClienteConfirmarEntregaPedidoService {
    PedidoReadResponseDTO confirmarEntrega(Long clienteId, PedidoConfirmaEntregaDTO patchBody);
}
