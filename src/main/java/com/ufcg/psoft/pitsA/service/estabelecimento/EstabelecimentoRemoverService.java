package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoDeleteDTO;

@FunctionalInterface
public interface EstabelecimentoRemoverService {
    void remover(Long id, EstabelecimentoDeleteDTO deleteBody);
}
