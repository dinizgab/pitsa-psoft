package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;

@FunctionalInterface
public interface SaborPatchDisponibilidade {
    SaborReadDTO alteraDisponibilidade(Long saborId);
}
