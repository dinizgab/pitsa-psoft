package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;

@FunctionalInterface
public interface SaborUpDateService {
    SaborDTO update(SaborDTO saborDTO);
}
