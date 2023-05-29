package com.ufcg.psoft.pitsA.model.pedido;

public enum TipoPagamento {
    PIX(0.05),
    DEBITO(0.025),
    CREDITO(0.0);

    private final Double fatorDesconto;

    TipoPagamento(Double fatorDesconto) {
        this.fatorDesconto = fatorDesconto;
    }

    public Double getFatorDesconto() {return this.fatorDesconto;}

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
