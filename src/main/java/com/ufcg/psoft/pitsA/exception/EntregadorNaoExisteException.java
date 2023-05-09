package com.ufcg.psoft.pitsA.exception;

public class EntregadorNaoExisteException extends PitsAException {
    public EntregadorNaoExisteException() {
        super("O Entregador consultado não existe!");
    }
}
