package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.ClienteRemoverPedidoDTO;

@FunctionalInterface
public interface ClienteRemoverPedidoService {
    void removerPedido(Long clienteId, ClienteRemoverPedidoDTO removeBody);
}
