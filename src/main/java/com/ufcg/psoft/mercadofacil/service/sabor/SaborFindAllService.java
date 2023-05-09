package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.SaborDTO;

import java.util.List;

@FunctionalInterface
public interface SaborFindAllService {
    List<SaborDTO> findAll();
}
