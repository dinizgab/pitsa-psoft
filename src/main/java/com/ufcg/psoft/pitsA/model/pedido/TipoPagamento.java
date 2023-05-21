package com.ufcg.psoft.pitsA.model.pedido;

public enum TipoPagamento {
    PIX,
    DEBITO,
    CREDITO;

    public boolean isPix() {
        return this == PIX;
    }

    public boolean isDebito() {
        return this == DEBITO;
    }

    public boolean isCredito() {
        return this == CREDITO;
    }
}
