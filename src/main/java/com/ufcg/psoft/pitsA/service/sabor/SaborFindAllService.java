package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;

import java.util.List;

@FunctionalInterface
public interface SaborFindAllService {
    List<SaborDTO> findAll();
}
