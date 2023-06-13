package com.ufcg.psoft.pitsA.exception.estabelecimento;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class NenhumEntregadorDisponivelException extends PitsAException {
    public NenhumEntregadorDisponivelException() {
        super("Nenhum Entregador esta disponivel no momento!");
    }
}
