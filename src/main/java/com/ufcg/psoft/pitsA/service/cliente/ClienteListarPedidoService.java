package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoListarDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadDTO;

import java.util.List;

@FunctionalInterface
public interface ClienteListarPedidoService {
    List<PedidoReadDTO> listarPedidos(Long clienteId, PedidoListarDTO listarPedidoDTO);
}
