package com.ufcg.psoft.pitsA.exception.estabelecimento;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class EstabelecimentoNaoExisteException extends PitsAException {
    public EstabelecimentoNaoExisteException() {
        super("O estabelecimento consultado nao existe!");
    }
}
