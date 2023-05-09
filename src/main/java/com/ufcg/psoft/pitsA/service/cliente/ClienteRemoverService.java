package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteDeleteDTO;

@FunctionalInterface
public interface ClienteRemoverService {
    void remover(Long id, ClienteDeleteDTO cliente);
}
