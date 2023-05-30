package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;

@FunctionalInterface
public interface EntregadorAdicionaPedidoService {
    void adicionaPedidoEntregador(Entregador entregador, Pedido pedido);
}
