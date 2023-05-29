package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.model.sabor.Sabor;

@FunctionalInterface
public interface EstabelecimentoRemoveSaborService {
    void removeSabor(Long id, Sabor sabor);
}
