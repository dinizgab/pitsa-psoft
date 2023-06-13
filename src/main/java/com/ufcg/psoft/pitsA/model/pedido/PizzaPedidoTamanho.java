package com.ufcg.psoft.pitsA.model.pedido;

public enum PizzaPedidoTamanho {
    GRANDE,
    MEDIA;

    public boolean isGrande() {
        return this == GRANDE;
    }

    public boolean isMedia() {
        return this == MEDIA;
    }
}
