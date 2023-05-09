package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;

@FunctionalInterface
public interface SaborFindByIdService {
    SaborDTO findById(Long id);
}
