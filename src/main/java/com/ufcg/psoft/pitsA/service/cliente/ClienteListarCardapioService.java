package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteCardapioDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;

import java.util.List;

@FunctionalInterface
public interface ClienteListarCardapioService {
    List<SaborReadDTO> listarCardapio(Long clienteId, ClienteCardapioDTO clienteCardapioDTO);
}
