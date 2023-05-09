package com.ufcg.psoft.mercadofacil.exception;

public class PitsAException extends RuntimeException {
    public PitsAException() {
        super("Erro inesperado no Mercado Fácil!");
    }

    public PitsAException(String message) {
        super(message);
    }
}
