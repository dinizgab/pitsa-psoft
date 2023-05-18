package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPutDTO;

@FunctionalInterface
public interface SaborUpdateService {
    SaborDTO update(Long estabelecimentoId, SaborPutDTO saborDTO);
}
