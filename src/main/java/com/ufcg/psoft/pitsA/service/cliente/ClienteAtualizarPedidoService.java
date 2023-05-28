package com.ufcg.psoft.pitsA.service.cliente;


import com.ufcg.psoft.pitsA.dto.pedido.PedidoPutDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

@FunctionalInterface
public interface ClienteAtualizarPedidoService {
    PedidoReadResponseDTO atualizarPedido(Long clienteId, PedidoPutDTO putBody);

}
