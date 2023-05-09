package com.ufcg.psoft.pitsA.service.entregador;

@FunctionalInterface
public interface AutenticaEmpregadoService {
    void autenticar(String codigoAtual, String codigoAutenticar);
}
