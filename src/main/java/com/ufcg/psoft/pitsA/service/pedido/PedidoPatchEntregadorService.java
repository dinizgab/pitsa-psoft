package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;

@FunctionalInterface
public interface PedidoPatchEntregadorService {
    PedidoReadResponseDTO alterarEntregador(Pedido pedido, Entregador entregador);
}
