package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;

@FunctionalInterface
public interface EstabelecimentoPatchEntregador {
    Estabelecimento alteraParcialmente(Long id, Entregador entregador);
}
