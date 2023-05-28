package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;

@FunctionalInterface
public interface ClientePatchInteresseSaborService {
    SaborReadDTO demonstraInteresse(Long clienteId, ClienteInteresseDTO clienteInteresseDTO);
}
