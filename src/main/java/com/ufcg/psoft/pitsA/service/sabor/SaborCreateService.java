package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborPostDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;

@FunctionalInterface
public interface SaborCreateService {
    SaborReadDTO create(Long estabelecimentoId, SaborPostDTO saborDTO);
}