package com.ufcg.psoft.pitsA.exception.entregador;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class EntregadorNaoEstaPendenteException extends PitsAException {
    public EntregadorNaoEstaPendenteException() {
        super("O Entregador consultado não esta na lista de pendencia desse estabelecimento!");
    }
}
