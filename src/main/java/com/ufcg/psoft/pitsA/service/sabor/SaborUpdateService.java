package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPutDTO;

@FunctionalInterface
public interface SaborUpdateService {
    SaborReadDTO update(Long estabelecimentoId, SaborPutDTO saborDTO);
}
