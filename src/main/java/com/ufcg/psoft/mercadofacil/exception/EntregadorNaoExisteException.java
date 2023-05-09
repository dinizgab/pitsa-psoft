package com.ufcg.psoft.mercadofacil.exception;

public class EntregadorNaoExisteException extends PitsAException {
    public EntregadorNaoExisteException() {
        super("O Entregador consultado não existe!");
    }
}
