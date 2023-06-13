package com.ufcg.psoft.pitsA.exception.pedido;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class TamanhoPedidoInvalidosException extends PitsAException {
    public TamanhoPedidoInvalidosException() {
        super("Peca uma pizza grande para fazer um pedido meio a meio");
    }
}
