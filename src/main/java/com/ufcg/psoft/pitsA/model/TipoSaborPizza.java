package com.ufcg.psoft.pitsA.model;

public enum TipoSaborPizza {
    SALGADO,
    DOCE;

    public boolean isSalgado() {
        return this == SALGADO;
    }

    public boolean isDoce() {
        return this == DOCE;
    }
}
