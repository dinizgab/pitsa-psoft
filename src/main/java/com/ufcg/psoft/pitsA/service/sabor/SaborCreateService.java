package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;

@FunctionalInterface
public interface SaborCreateService {
    SaborDTO create(SaborDTO saborDTO);
}