package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.model.pedido.Pedido;

@FunctionalInterface
public interface PedidoCriarService {
    Pedido criar(Pedido pedido);
}
