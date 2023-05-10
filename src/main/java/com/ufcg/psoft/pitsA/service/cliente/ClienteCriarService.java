package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import org.springframework.beans.factory.annotation.Autowired;

@FunctionalInterface
public interface ClienteCriarService {
    ClienteReadDTO salvar(ClientePostPutDTO cliente);
}
