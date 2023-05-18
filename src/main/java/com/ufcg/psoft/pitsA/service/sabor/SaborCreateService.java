package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPostDTO;

@FunctionalInterface
public interface SaborCreateService {
    SaborReadDTO create(Long estabelecimentoId, SaborPostDTO saborDTO);
}