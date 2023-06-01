package com.ufcg.psoft.pitsA.exception.pedido;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class PedidoJaEstaProntoException extends PitsAException {
    public PedidoJaEstaProntoException() {super("Não é possível cancelar o pedido, porque ele já está pronto!");}
}
