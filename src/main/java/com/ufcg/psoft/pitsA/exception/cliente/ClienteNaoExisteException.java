package com.ufcg.psoft.pitsA.exception.cliente;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class ClienteNaoExisteException extends PitsAException {
    public ClienteNaoExisteException() {
        super("O Cliente consultado não existe!");
    }
}
