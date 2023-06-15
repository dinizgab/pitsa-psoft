package com.ufcg.psoft.pitsA.model.entregador;

public enum TipoVeiculoEntregador {
    CARRO("Carro"),
    MOTO("Moto");
    private final String tipoVeiculo;

     TipoVeiculoEntregador(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getTipoVeiculo() {
        return this.tipoVeiculo;
    }
}
