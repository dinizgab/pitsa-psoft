package com.ufcg.psoft.pitsA.model.sabor;

public enum TipoSabor {
    SALGADO,
    DOCE;

    public boolean isSalgado() {
        return this == SALGADO;
    }

    public boolean isDoce() {
        return this == DOCE;
    }
}
