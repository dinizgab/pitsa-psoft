package com.ufcg.psoft.pitsA.model.pedido;

public enum EstadoPedido {
    // TODO - Estado padrão do Pedido (Começa com esse);
    RECEBIDO,

    // TODO - Quando o Cliente confirma o pagamento do Pedido;
    EM_PREPARO,

    // TODO - Quando o Estabelecimento alterar o estado;
    PRONTO,

    // TODO - Quando associamos um Entregador a um Pedido;
    EM_ROTA,

    // TODO - Quando o Cliente confirmar a entrega do Pedido;
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
