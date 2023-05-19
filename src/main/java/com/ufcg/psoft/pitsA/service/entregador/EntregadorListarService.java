package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;

import java.util.List;

@FunctionalInterface
public interface EntregadorListarService {
    List<EntregadorReadDTO> listar(Long id);
}
