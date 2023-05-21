package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;

@FunctionalInterface
public interface ClienteCriarService {
    ClienteReadDTO salvar(ClientePostPutDTO cliente);
}
