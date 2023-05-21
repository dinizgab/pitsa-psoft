package com.ufcg.psoft.pitsA.model.pedido;

import com.ufcg.psoft.pitsA.model.sabor.SaborPedido;

import java.util.List;

@FunctionalInterface
public interface CalculadoraPedido {
    double calculaTotal(List<SaborPedido> saboresPedido, PizzaPedidoTipo tipoPedido, PizzaPedidoTamanho tamanho);
}
