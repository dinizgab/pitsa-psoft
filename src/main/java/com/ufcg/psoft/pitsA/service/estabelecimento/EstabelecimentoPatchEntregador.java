package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPatchEntregadorDTO;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoPatchEntregador {
    Estabelecimento alteraParcialmente(Long id, Entregador entregador);
}
