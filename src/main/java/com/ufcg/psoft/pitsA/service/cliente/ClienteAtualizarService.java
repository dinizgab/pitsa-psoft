package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;

@FunctionalInterface
public interface ClienteAtualizarService {
    ClienteReadDTO alterar(Long id, ClientePostPutDTO cliente);
}
