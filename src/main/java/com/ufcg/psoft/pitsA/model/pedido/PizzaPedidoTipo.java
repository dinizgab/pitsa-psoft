package com.ufcg.psoft.pitsA.model.pedido;

public enum PizzaPedidoTipo {
    INTEIRA(1.0),
    MEIA(0.5);

    private final Double fatorTipo;

    PizzaPedidoTipo(Double fatorTipo) {
        this.fatorTipo = fatorTipo;
    }

    public Double getFatorTipo() {
        return this.fatorTipo;
    }

    public boolean isInteira() {
        return this == INTEIRA;
    }

    public boolean isMeia() {
        return this == MEIA;
    }
}
