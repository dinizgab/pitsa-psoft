package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

import java.util.List;

@FunctionalInterface
public interface ClienteListarPedidoService {
    List<PedidoReadResponseDTO> listarPedidos(Long clienteId, PedidoReadBodyDTO listarPedidoDTO);
}
