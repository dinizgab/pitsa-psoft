package com.ufcg.psoft.pitsA.dto.estabelecimento;

public enum StatusAprovacao {
    APROVADO,
    REJEITADO;

    public boolean isAprovado() {
        return this == APROVADO;
    }
}
