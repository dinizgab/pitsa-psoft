package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoValidaDTO;
import com.ufcg.psoft.pitsA.exception.pedido.TamanhoPedidoInvalidosException;

@FunctionalInterface
public interface ValidaPedidoService {
    void validaPedido(PedidoValidaDTO validaDTO) throws TamanhoPedidoInvalidosException;
}
