package com.ufcg.psoft.pitsA.exception.entregador;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class EntregadorNaoEstaAprovadoException extends PitsAException {
    public EntregadorNaoEstaAprovadoException() {
        super("O Entregador consultado nao esta na lista de entregadores aprovados desse estabelecimento!");
    }
}
