package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoPostDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;

@FunctionalInterface
public interface ClienteCriarPedidoService {
    PedidoReadResponseDTO criarPedido(Long id, PedidoPostDTO postBody);
}
