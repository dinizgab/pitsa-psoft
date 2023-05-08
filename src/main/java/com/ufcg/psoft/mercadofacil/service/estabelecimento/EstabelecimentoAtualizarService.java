package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPutPostDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAtualizarService {
    Estabelecimento atualizar(Long id, EstabelecimentoPutPostDTO estabelecimentoPutPostDTO);
}
