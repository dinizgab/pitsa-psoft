package com.ufcg.psoft.pitsA.model.pedido;

import com.ufcg.psoft.pitsA.model.sabor.Sabor;

import java.util.List;

@FunctionalInterface
public interface CalculadoraPedido {
    double calculaTotal(List<Sabor> saboresPedido, PizzaPedidoTipo tipoPedido, PizzaPedidoTamanho tamanho, TipoPagamento tipoPagamento);
}
