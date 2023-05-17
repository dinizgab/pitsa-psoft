package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPostDTO;

@FunctionalInterface
public interface SaborCreateService {
    SaborDTO create(Long estabelecimentoId, SaborPostDTO saborDTO);
}