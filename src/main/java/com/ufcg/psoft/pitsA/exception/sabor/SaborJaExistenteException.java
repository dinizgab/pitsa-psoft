package com.ufcg.psoft.pitsA.exception.sabor;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class SaborJaExistenteException extends PitsAException {
    public SaborJaExistenteException() {
        super("Sabor já existente");
    }
}
