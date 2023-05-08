package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorPatchEstabelecimentoService {
    Entregador alteraParcialmente(Long entregadorId, Long estabelecimentoId, String codigoAcesso);
}
