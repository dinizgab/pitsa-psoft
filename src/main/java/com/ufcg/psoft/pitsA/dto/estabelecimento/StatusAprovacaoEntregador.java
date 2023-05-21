package com.ufcg.psoft.pitsA.dto.estabelecimento;

public enum StatusAprovacaoEntregador {
    APROVADO,
    REJEITADO;

    public boolean isAprovado() {
        return this == APROVADO;
    }
}
