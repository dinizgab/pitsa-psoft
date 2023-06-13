package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;

@FunctionalInterface
public interface EntregadorAprovaEntregadorService {
    void aprovar(Entregador entregador, Estabelecimento estabelecimento);
}
