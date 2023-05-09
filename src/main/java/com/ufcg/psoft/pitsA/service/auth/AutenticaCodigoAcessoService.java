package com.ufcg.psoft.pitsA.service.auth;

@FunctionalInterface
public interface AutenticaCodigoAcessoService {
    void autenticar(String codigoAtual, String codigoAutenticar);
}
