package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.ClienteListarPedidoDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadDTO;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;

import java.util.List;

@FunctionalInterface
public interface ClienteListarPedidoService {
    List<PedidoReadDTO> listarPedidos(Long clienteId, ClienteListarPedidoDTO listarPedidoDTO);
}
