package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;

@FunctionalInterface
public interface ClientePatchInteresseSaborService {
    ClienteReadDTO demonstraInteresse(Long clienteId, ClienteInteresseDTO clienteInteresseDTO);
}
