package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.mercadofacil.dto.EntregadorReadDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorAtualizarService {
    EntregadorReadDTO atualizar(Long id, EntregadorPostPutDTO entregador);
}
