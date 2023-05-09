package com.ufcg.psoft.pitsA.exception.entregador;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class EntregadorNaoExisteException extends PitsAException {
    public EntregadorNaoExisteException() {
        super("O Entregador consultado não existe!");
    }
}
