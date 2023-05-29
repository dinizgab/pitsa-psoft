package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDeleteDTO;

@FunctionalInterface
public interface SaborRemoverService {
    void remover(Long id, SaborDeleteDTO saborDeleteDTO);
}
