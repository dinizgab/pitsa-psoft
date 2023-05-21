package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoListarDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadDTO;

import java.util.List;

@FunctionalInterface
public interface EstabelecimentoListarPedidoService {
    List<PedidoReadDTO> listarPedidos(Long estabelecimentoId, PedidoListarDTO listarBody);
}
