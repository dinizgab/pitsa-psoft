package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoValidaDTO;
import com.ufcg.psoft.pitsA.exception.pedido.TamanhoPedidoInvalidosException;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;

@FunctionalInterface
public interface ValidaPedidoService {
    void validaPedido(PedidoValidaDTO validaDTO) throws TamanhoPedidoInvalidosException;
}
