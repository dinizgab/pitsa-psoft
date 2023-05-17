package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.model.Sabor;

@FunctionalInterface
public interface EstabelecimentoAtualizaSaborService {
    void atualizaSabor(Long id, Sabor sabor);
}
