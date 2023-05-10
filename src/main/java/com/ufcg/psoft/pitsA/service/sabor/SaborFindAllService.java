package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDTO;

import java.util.List;

@FunctionalInterface
public interface SaborFindAllService {
    List<SaborDTO> findAll();
}
