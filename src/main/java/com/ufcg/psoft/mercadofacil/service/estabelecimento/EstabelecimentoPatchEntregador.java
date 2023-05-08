package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoEntregadorDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoPatchEntregador {
    Estabelecimento alteraParcialmente(Long id, EstabelecimentoEntregadorDTO estabelecimentoPatch);
}
