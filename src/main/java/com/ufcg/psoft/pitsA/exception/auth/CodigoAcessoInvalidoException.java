package com.ufcg.psoft.pitsA.exception.auth;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class CodigoAcessoInvalidoException extends PitsAException {
    public CodigoAcessoInvalidoException() {
        super("O codigo de acesso informado eh invalido");
    }
}
