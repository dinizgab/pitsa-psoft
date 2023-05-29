package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.model.sabor.Sabor;

@FunctionalInterface
public interface EstabelecimentoAdicionaSaborService {
    void adicionaSabor(Long id, Sabor sabor);
}
