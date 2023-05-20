package com.ufcg.psoft.pitsA.model.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.SaborPedidoDTO;

import java.util.List;

public class CalculadoraPedidoImpl implements CalculadoraPedido {
    public double calculaTotal(List<SaborPedidoDTO> sabores, PizzaPedidoTipo pedidoTipo, PizzaPedidoTamanho tamanho) {
        double total = 0.0;

        // TODO - Tirar esses ifs usando heranca dentro do SaborPedidoDTO, pra ter so um preco dependendo do tamanho e so um getter
        for (SaborPedidoDTO sabor : sabores) {
            if (tamanho.isMedia()) total += sabor.getPrecoMedio();
            else total += sabor.getPrecoGrande();
        }

        if (pedidoTipo.isMeia()) total = total / 2;
        return total;
    }
}
