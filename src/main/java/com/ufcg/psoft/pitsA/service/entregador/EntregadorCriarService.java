package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorPostPutDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;

@FunctionalInterface
public interface EntregadorCriarService {
    EntregadorReadDTO salvar(EntregadorPostPutDTO entregador);
}
