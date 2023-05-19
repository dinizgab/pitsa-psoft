package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorDeleteDTO;

@FunctionalInterface
public interface EntregadorRemoverService {
    void remover(Long id, EntregadorDeleteDTO deleteBodyDTO);
}
