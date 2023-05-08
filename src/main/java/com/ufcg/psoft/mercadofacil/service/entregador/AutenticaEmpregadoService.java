package com.ufcg.psoft.mercadofacil.service.entregador;

@FunctionalInterface
public interface AutenticaEmpregadoService {
    void autenticar(String codigoAtual, String codigoAutenticar);
}
