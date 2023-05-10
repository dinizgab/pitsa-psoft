package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;

import java.util.List;

@FunctionalInterface
public interface ClienteListarService {
    List<ClienteReadDTO> listar(Long id);
}
