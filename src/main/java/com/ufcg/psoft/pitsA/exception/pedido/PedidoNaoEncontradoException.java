package com.ufcg.psoft.pitsA.exception.pedido;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class PedidoNaoEncontradoException extends PitsAException {
    public PedidoNaoEncontradoException() {super("O pedido requisitado nao foi encontrado!");}
}
