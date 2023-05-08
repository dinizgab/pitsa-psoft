package com.ufcg.psoft.pitsA.exception.saborException;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class SaborNaoExistenteException extends PitsAException {
    public SaborNaoExistenteException() {
        super("Sabor não encontrado");
    }
}
