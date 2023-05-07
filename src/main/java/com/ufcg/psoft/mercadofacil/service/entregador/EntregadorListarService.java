package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.EntregadorReadDTO;

import java.util.List;

@FunctionalInterface
public interface EntregadorListarService {
    List<EntregadorReadDTO> listar(Long id);
}
