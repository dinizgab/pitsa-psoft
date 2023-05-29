package com.ufcg.psoft.pitsA.exception.sabor;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class SaborEstaDisponivelException extends PitsAException {

    public SaborEstaDisponivelException() {
        super("Sabor já esta disponivel");
    }
}
