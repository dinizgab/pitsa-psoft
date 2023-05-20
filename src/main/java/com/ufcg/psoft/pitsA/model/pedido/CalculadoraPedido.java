package com.ufcg.psoft.pitsA.model.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.SaborPedidoDTO;

import java.util.List;

@FunctionalInterface
public interface CalculadoraPedido {
    double calculaTotal(List<SaborPedidoDTO> saboresPedido, PizzaPedidoTipo tipoPedido, PizzaPedidoTamanho tamanho);
}
