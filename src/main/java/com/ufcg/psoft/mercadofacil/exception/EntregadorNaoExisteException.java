package com.ufcg.psoft.mercadofacil.exception;

public class EntregadorNaoExisteException extends PitsAException {
    public EntregadorNaoExisteException() {
        super("O produto consultado não existe!");
    }
}
