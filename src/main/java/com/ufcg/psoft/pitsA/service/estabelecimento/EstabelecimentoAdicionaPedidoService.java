package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;

@FunctionalInterface
public interface EstabelecimentoAdicionaPedidoService {
    void adicionaPedido(Estabelecimento estabelecimento, Pedido pedido);
}
