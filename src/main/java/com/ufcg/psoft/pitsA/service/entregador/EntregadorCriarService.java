package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;

@FunctionalInterface
public interface EntregadorCriarService {
    EntregadorReadDTO salvar(EntregadorPostPutDTO entregador);
}
