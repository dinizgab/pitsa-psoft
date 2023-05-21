package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;

import java.util.List;

@FunctionalInterface
public interface EstabelecimentoListarPedidoService {
    List<PedidoReadResponseDTO> listarPedidos(Long estabelecimentoId, PedidoReadBodyDTO listarBody);
}
