package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.SaborDTO;

@FunctionalInterface
public interface SaborCreateService {
    SaborDTO create(SaborDTO saborDTO);
}