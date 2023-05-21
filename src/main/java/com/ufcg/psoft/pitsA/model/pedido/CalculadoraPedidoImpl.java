package com.ufcg.psoft.pitsA.model.pedido;

import com.ufcg.psoft.pitsA.model.sabor.Sabor;

import java.util.List;

public class CalculadoraPedidoImpl implements CalculadoraPedido {
    public double calculaTotal(List<Sabor> sabores, PizzaPedidoTipo pedidoTipo, PizzaPedidoTamanho tamanho, TipoPagamento tipoPagamento) {
        double total = 0.0;

        // TODO - Tirar esses ifs usando heranca dentro do SaborPedido, pra ter so um preco dependendo do tamanho e so um getter
        for (Sabor sabor : sabores) {
            if (tamanho.isMedia()) total += sabor.getPrecoMedio();
            else total += sabor.getPrecoGrande();
        }

        if (pedidoTipo.isMeia()) total = total / 2;
        if (tipoPagamento != null) total = total - (total * tipoPagamento.getFatorDesconto());

        return total;
    }
}
