package com.ufcg.psoft.pitsA.model.pedido;

public enum PizzaPedidoTipo {
    INTEIRA,
    MEIA;

    public boolean isInteira() {
        return this == INTEIRA;
    }

    public boolean isMeia() {
        return this == MEIA;
    }
}
