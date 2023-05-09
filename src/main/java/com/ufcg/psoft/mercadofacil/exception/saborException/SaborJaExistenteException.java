package com.ufcg.psoft.mercadofacil.exception.saborException;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class SaborJaExistenteException extends PitsAException {
    public SaborJaExistenteException() {
        super("Sabor já existente");
    }
}
