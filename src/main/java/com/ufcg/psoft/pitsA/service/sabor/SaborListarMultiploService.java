package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.model.sabor.Sabor;

import java.util.List;

@FunctionalInterface
public interface SaborListarMultiploService {
    List<Sabor> listarMultiplos(List<Long> saboresId);
}
