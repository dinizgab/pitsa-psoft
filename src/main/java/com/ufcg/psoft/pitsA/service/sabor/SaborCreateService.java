package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDTO;

@FunctionalInterface
public interface SaborCreateService {
    SaborDTO create(Long estabelecimentoId, SaborDTO saborDTO);
}