package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.EstabelecimentoPutPostDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAtualizarService {
    Estabelecimento atualizar(Long id, EstabelecimentoPutPostDTO estabelecimentoPutPostDTO);
}
