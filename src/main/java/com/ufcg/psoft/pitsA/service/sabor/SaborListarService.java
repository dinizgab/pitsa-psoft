package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.model.Sabor;

import java.util.List;

@FunctionalInterface
public interface SaborListarService {
    List<Sabor> listar(Long id);
}
