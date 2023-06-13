package com.ufcg.psoft.pitsA.model.pedido;

public enum EstadoPedido {
    RECEBIDO,
    EM_PREPARO,
    PRONTO,
    EM_ROTA,
    ENTREGUE;

    public boolean isRecebido() {
        return this == RECEBIDO;
    }

    public boolean isPreparo() {
        return this == EM_PREPARO;
    }

    public boolean isPronto() {
        return this == PRONTO;
    }

    public boolean isRota() {
        return this == EM_ROTA;
    }

    public boolean isEntregue() {
        return this == ENTREGUE;
    }
}
