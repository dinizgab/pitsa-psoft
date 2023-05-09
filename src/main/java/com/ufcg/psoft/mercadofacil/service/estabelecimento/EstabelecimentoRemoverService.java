package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoDeleteDTO;

@FunctionalInterface
public interface EstabelecimentoRemoverService {
    void remover(Long id, EstabelecimentoDeleteDTO deleteDTO) throws Exception;
}
